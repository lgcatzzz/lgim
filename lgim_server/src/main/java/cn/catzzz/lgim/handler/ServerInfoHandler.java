package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.cache.LocalCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Set;

/**
 * Classname:   ServerInfoHandler<br/>
 * <b>Description: 输出服务端基本信息（如用户缓存）</b><br/>
 * Date: 2022/12/24 19:26<br/>
 * Created by gql
 */
public class ServerInfoHandler extends ChannelInboundHandlerAdapter {
    private final LocalCache cache;

    public ServerInfoHandler(LocalCache cache) {
        this.cache = cache;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 作为服务端最后一个Handler，无需再向后传递消息（下同）
        outputUserCache();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("ServerInfoHandler: inactive");
        outputUserCache();
    }

    public void outputUserCache() {
        Set<String> keys = cache.cacheKeys();
        System.out.println("\n\ruser cache:");
        for (String key :
                keys) {
            System.out.print(key + "  ");
            System.out.println(cache.get(key));
        }
        System.out.println("total users: " + keys.size() + "\n");
    }
}
