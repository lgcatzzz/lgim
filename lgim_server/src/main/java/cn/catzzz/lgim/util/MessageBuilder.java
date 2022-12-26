package cn.catzzz.lgim.util;

import cn.catzzz.lgim.constant.MsgTypeConstants;
import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;

/**
 * Classname:   MessageBuilder<br/>
 * Description: <br/>
 * Date: 2022/12/24 16:28<br/>
 * Created by gql
 */
public class MessageBuilder {

    /**
     * 根据ProtobufMessage构建Message
     */
    public static Message buildMessage(MessageProtobuf.Msg protoMsg) {
        if (protoMsg == null) {
            return null;
        }
        Message message = new Message();
        MessageProtobuf.Head head = protoMsg.getHead();
        message.setMsgId(head.getMsgId());
        message.setMsgType(head.getMsgType());
        message.setMsgContentType(head.getMsgContentType());
        message.setFromId(head.getFromId());
        message.setToId(head.getToId());
        message.setTimestamp(head.getTimestamp());
        message.setStatus(head.getStatus());
        message.setExtend(head.getExtend());
        message.setBody(protoMsg.getBody());
        return message;
    }

    /**
     * 根据Message构建ProtobufMessage
     */
    public static MessageProtobuf.Msg buildMessageProtobuf(Message message) {
        if (message == null) {
            return null;
        }
        MessageProtobuf.Msg.Builder builder = MessageProtobuf.Msg.newBuilder();
        MessageProtobuf.Head.Builder headBuilder = MessageProtobuf.Head.newBuilder();
        headBuilder.setMsgId(message.getMsgId());
        headBuilder.setMsgType(message.getMsgType());
        headBuilder.setMsgContentType(message.getMsgContentType());
        headBuilder.setFromId(message.getFromId());
        headBuilder.setToId(message.getToId());
        headBuilder.setTimestamp(message.getTimestamp());
        headBuilder.setStatus(message.getStatus());
        headBuilder.setExtend(message.getExtend());
        MessageProtobuf.Head head = headBuilder.build();
        builder.setHead(head);
        builder.setBody(message.getBody());
        return builder.build();
    }

    public static MessageProtobuf.Msg buildPongMessageProtobuf(String toId) {
        Message message = new Message().buildDefaultMessage();
        message.setMsgType(MsgTypeConstants.HEARTBEAT_RESP);
        message.setStatus(MsgTypeConstants.SERVER_FORWARD);
        message.setToId(toId);
        return buildMessageProtobuf(message);
    }
}
