package cn.catzzz.lgim;

import cn.catzzz.lgim.cache.LocalCache;
import cn.catzzz.lgim.handler.ChannelInactiveHandler;
import cn.catzzz.lgim.handler.HeartbeatHandler;
import cn.catzzz.lgim.handler.LoginAuthHandler;
import cn.catzzz.lgim.handler.ServerInfoHandler;
import cn.catzzz.lgim.listener.LoginEventListener;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Classname:   ServerChannelInitializer<br/>
 * Description: <br/>
 * Date: 2022/12/20 20:35<br/>
 * Created by gql
 */
@Slf4j
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final LocalCache cache;
    private LoginEventListener loginEventListener;

    public ServerChannelInitializer(LocalCache cache) {
        this.cache = cache;
    }

    public ServerChannelInitializer(LocalCache cache, LoginEventListener loginEventListener) {
        this.cache = cache;
        this.loginEventListener = loginEventListener;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new LoggingHandler());    // 日志打印
        ch.pipeline().addLast(new IdleStateHandler(
                0, 0, 10));
        ch.pipeline().addLast(new ProtobufDecoder(MessageProtobuf.Msg.getDefaultInstance()));
        ch.pipeline().addLast(new ProtobufEncoder());
        ch.pipeline().addLast(new HeartbeatHandler());  // 心跳处理器
        ch.pipeline().addLast(new LoginAuthHandler(cache, loginEventListener));
        ch.pipeline().addLast(new ChannelInactiveHandler(cache));
        ch.pipeline().addLast(new ServerInfoHandler(cache));
        //todo 2022/12/24 已完成用户登录及登录成功后对连接的缓存，被迫中断连接（Inactive事件）移除用户连接缓存
        // 登录成功/失败后，服务端需要返回相应的响应信息
        // 对于登录，是否再抽象出一层接口专门用于处理登录逻辑
        // 客户端容易封装，服务端不太容易封装
    }
}
