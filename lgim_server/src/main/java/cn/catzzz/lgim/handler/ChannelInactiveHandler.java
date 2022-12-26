package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.cache.LocalCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classname:   ChannelInactiveHandler<br/>
 * Description: 当Channel处于Inactive时会用到此类<br/>
 * Date: 2022/12/20 21:19<br/>
 * Created by gql
 */
@Slf4j
public class ChannelInactiveHandler extends ChannelInboundHandlerAdapter {
    private final LocalCache cache;

    public ChannelInactiveHandler(LocalCache cache) {
        this.cache = cache;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.debug(ctx.channel().id().asLongText() + ": active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug(ctx.channel().id().asLongText() + ": inactive");
        //todo 处理（客户端异常）下线逻辑，调用上层回调接口?如果这样，缓存也应属于上层
        Object obj = cache.removeFirstByValue(ctx.channel().id().asLongText());
        System.out.println("从缓存系统移除Channel关系, id=" + obj);
        ctx.fireChannelInactive();
    }
}
