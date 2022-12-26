package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import cn.catzzz.lgim.util.MessageBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Classname:   AbstractInboundHandler<br/>
 * Description: <br/>
 * Date: 2022/12/24 18:44<br/>
 * Created by gql
 */
public abstract class AbstractInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        MessageProtobuf.Msg msg = (MessageProtobuf.Msg) obj;
        Message message = MessageBuilder.buildMessage(msg);
        String channelId = ctx.channel().id().asLongText();
    }
}
