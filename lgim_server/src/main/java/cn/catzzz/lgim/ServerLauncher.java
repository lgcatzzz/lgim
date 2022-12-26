package cn.catzzz.lgim;

import cn.catzzz.lgim.cache.ConcurrentHashMapCache;
import cn.catzzz.lgim.cache.LocalCache;
import cn.catzzz.lgim.listener.LoginEventListener;
import cn.catzzz.lgim.listener.impl.DefaultLoginEventListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Classname:   ServerLauncher<br/>
 * Description: 服务端启动类<br/>
 * Date: 2022/12/18 22:34<br/>
 * Created by gql
 */
@Slf4j
public class ServerLauncher {
    public static final int PORT = 9421;
    private Channel serverChannel;
    private LocalCache cache;

    private final LoginEventListener loginEventListener;

    public ServerLauncher(LoginEventListener loginEventListener) {
        this.loginEventListener = loginEventListener;
    }

    public static void main(String[] args) {
        ServerLauncher serverLauncher = new ServerLauncher(new DefaultLoginEventListener());
        serverLauncher.init();
        serverLauncher.startup();
    }

    /**
     * 初始化服务
     */
    public void init() {
        //todo 上层回调接口
        cache = new ConcurrentHashMapCache();
    }

    /**
     * 开启服务
     */
    public void startup() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
//        bootstrap.childHandler(new ServerChannelInitializer(cache));
        bootstrap.childHandler(new ServerChannelInitializer(cache, loginEventListener));
        // 设置TCP参数
        // 链接缓冲池的大小
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 维持活跃链接（TCP层面）
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        // 关闭延迟发送
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        // 绑定端口
        ChannelFuture future = bootstrap.bind(PORT);
        serverChannel = future.channel();
        //        System.out.println("-----server startup-----");
        log.debug("-----server startup, port=" + PORT + "-----");
        try {
            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            // 优雅退出，释放线程池资源
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.debug("-----server shutdown-----");
        }
    }

    /**
     * 关闭服务
     */
    public void shutdown() {
        if (serverChannel != null) {
            serverChannel.close();
            serverChannel = null;
            log.debug("-----server shutdown-----");
        }
    }
}
