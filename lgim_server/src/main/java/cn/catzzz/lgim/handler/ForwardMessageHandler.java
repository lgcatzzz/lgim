package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import cn.catzzz.lgim.util.MessageBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Classname:   ForwardMessageHandler<br/>
 * Description: <br/>
 * Date: 2022/12/20 21:07<br/>
 * Created by gql
 */
public class ForwardMessageHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        // 将Object msg强转为Message类型
        // 获取接收方Id
        // 通过缓存判断接收方是否在线
        // 若在线，找到接收方对应的Channel
        // 将该消息发送到接收方Channel
        // 若不在线，进行离线存储等业务
        //todo 不考虑业务逻辑（是否是好友关系），只转发消息
        // 可以设计一套算法使之能更快地完成消息的转发
        MessageProtobuf.Msg msg = (MessageProtobuf.Msg) obj;
        Message message = MessageBuilder.buildMessage(msg);
        String channelId = ctx.channel().id().asLongText();
        // todo 可以把这些Handler的read事件合并在一个Handler中，即在一个Handler中根据消息类型的不同调用不同的方法
        ctx.fireChannelRead(msg);
    }
}
