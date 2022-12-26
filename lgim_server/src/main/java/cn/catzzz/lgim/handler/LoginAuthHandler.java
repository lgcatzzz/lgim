package cn.catzzz.lgim.handler;

import cn.catzzz.lgim.cache.LocalCache;
import cn.catzzz.lgim.constant.MsgTypeConstants;
import cn.catzzz.lgim.listener.LoginEventListener;
import cn.catzzz.lgim.model.Message;
import cn.catzzz.lgim.protobuf.MessageProtobuf;
import cn.catzzz.lgim.util.MessageBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Classname:   LoginAuthHandler<br/>
 * Description: 登录处理器（保存用户信息）<br/>
 * Date: 2022/12/24 15:14<br/>
 * Created by gql
 */
public class LoginAuthHandler extends ChannelInboundHandlerAdapter {
    private final LocalCache cache;
    private final LoginEventListener loginEventListener;  // 上层回调接口

    public LoginAuthHandler(LocalCache cache) {
        this.cache = cache;
        loginEventListener = null;
    }

    public LoginAuthHandler(LocalCache cache, LoginEventListener loginEventListener) {
        this.cache = cache;
        this.loginEventListener = loginEventListener;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        MessageProtobuf.Msg msg = (MessageProtobuf.Msg) obj;
        Message message = MessageBuilder.buildMessage(msg);
        String channelId = ctx.channel().id().asLongText();
        if (message.getMsgType() == MsgTypeConstants.LOGIN) {  // 处理登录消息
            //todo 保存用户信息
            // 目前以用户Id作为会话Id，每个用户都与服务端建立一个连接。还要能区分不同设备登录
            //可能出现以下的情况会执行if：同一客户端同一账号发送多次登录请求
            if (cache.containsValue(channelId)) {   // 该Channel已经被占用
                cache.removeAllByValue(channelId);
            }
            cache.put(message.getFromId(), channelId);
            if (loginEventListener != null) {
                loginEventListener.onLogin(message);    // 回调事件
            }
        }
        ctx.fireChannelRead(obj);
    }
}
