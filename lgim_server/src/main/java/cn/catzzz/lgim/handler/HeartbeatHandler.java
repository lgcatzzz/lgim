package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.constant.MsgTypeConstants;
import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import cn.catzzz.lgim.util.MessageBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;

/**
 * Classname:   HeartbeatHandler<br/>
 * <b>Description: 处理客户端发来的心跳包</b><br/>
 * Date: 2022/12/26 16:11<br/>
 * Created by gql
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            System.out.println("心跳超时 channelId: " + ctx.channel().id().asLongText());
            ctx.disconnect();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        Message message = MessageBuilder.buildMessage((MessageProtobuf.Msg) obj);
        Channel channel = ctx.channel();
        String fromId = message.getFromId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = message.getTimestamp();
        String sd = sdf.format(timestamp);
        //todo 即使用户没有登录也能发送心跳包
        // 原因：现在的客户端代码是一旦启动就会与服务端建立一条连接。正常情况下只有用户登录时才会建立连接。
        if (message.getMsgType() == MsgTypeConstants.HEARTBEAT) {
            System.out.printf("%s  收到来自[%s]的心跳包\n", sd, fromId);
            MessageProtobuf.Msg pong = MessageBuilder.buildPongMessageProtobuf(fromId);
            channel.writeAndFlush(pong);
        }
        ctx.fireChannelRead(obj);
    }
}
