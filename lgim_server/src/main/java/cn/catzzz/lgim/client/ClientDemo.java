package cn.catzzz.lgim.client;

import cn.catzzz.lgim.constant.MsgTypeConstants;
import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import cn.catzzz.lgim.util.MessageBuilder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Classname:   ClientDemo<br/>
 * Description: <br/>
 * Date: 2022/12/24 17:11<br/>
 * Created by gql
 */
public class ClientDemo {

    private static Channel channel;
    private static String userId = "9421";

    public static void main(String[] args) throws InterruptedException {
        start();
        System.out.println("选择发送消息类型");
        System.out.println("1：登录消息，2：心跳消息，3：文本消息");
        for (int i = 0; i < 10; i++) {
            Scanner sc = new Scanner(System.in);
            int a = 1;
            a = sc.nextInt();
            sendMsg(channel, a);
            Thread.sleep(100);
        }
    }

    public static void start() throws InterruptedException {
        channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    // 在连接建立后会调用
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new ProtobufDecoder(MessageProtobuf.Msg.getDefaultInstance()));
                        ch.pipeline().addLast(new ProtobufEncoder());
                        ch.pipeline().addLast(new ClientReadHandler());
                    }
                })
                .connect(new InetSocketAddress("localhost", 9421))
                // 阻塞方法，直到连接建立才会继续执行
                .sync()
                // 得到连接对象Channel
                .channel();
    }

    private static void sendMsg(Channel channel, int a) {
        Message message = new Message();
        message.setMsgId(String.valueOf(Math.random() * 100));

        message.setToId("server");
        message.setStatus(MsgTypeConstants.CLIENT_SENT);
        switch (a) {
            case 1:     // 登录
                userId = String.valueOf(Math.random() * 1000);
                message.setFromId(userId);
                message.setMsgType(MsgTypeConstants.LOGIN);
                message.setMsgContentType(MsgTypeConstants.NULL_CONTENT);
                message.setBody("");
                break;
            case 2:     // 心跳
                message.setFromId(userId);
                message.setMsgType(MsgTypeConstants.HEARTBEAT);
                message.setMsgContentType(MsgTypeConstants.NULL_CONTENT);
                message.setBody("");
                break;
            case 3:     // 文本
                message.setFromId(userId);
                message.setMsgType(MsgTypeConstants.SINGLE_CHAT);
                message.setMsgContentType(MsgTypeConstants.TEXT_CONTENT);
                message.setBody("hello world");
                break;
            default:
                System.out.println("输入内容有误");
                return;
        }
        message.setExtend("");
        message.setTimestamp(System.currentTimeMillis());
        System.out.println("发送消息：\n" + message);
        MessageProtobuf.Msg msg = MessageBuilder.buildMessageProtobuf(message);
        channel.writeAndFlush(msg);
    }
}

class ClientReadHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        System.out.println("收到来自服务端的消息：");
        Message msg = MessageBuilder.buildMessage((MessageProtobuf.Msg) obj);
        System.out.println(msg);
    }
}
