package cn.catzzz.lgim.constant;

/**
 * Classname:   MsgTypeConstants<br/>
 * Description: 消息类型<br/>
 * Date: 2022/12/24 14:24<br/>
 * Created by gql
 */
public class MsgTypeConstants {

    public static final int SERVER_ID = 0;  // 当消息由服务端发出时，fromId设为0
    /* 消息类型（1***） */
    /**
     * 登录类型消息
     */
    public static final int LOGIN = 1001;
    /**
     * 心跳消息
     */
    public static final int HEARTBEAT = 1002;
    /**
     * 心跳消息的响应
     */
    public static final int HEARTBEAT_RESP = 1003;
    /**
     * 单聊消息
     */
    public static final int SINGLE_CHAT = 1004;
    /**
     * 群聊消息
     */
    public static final int GROUP_CHAT = 1005;

    // 消息内容类型（1**），只在单聊或群聊中生效
    public static final int NULL_CONTENT = 100;     // 非单聊或群聊消息时，将MsgContentType设为此值
    public static final int TEXT_CONTENT = 101;     // 文本类型内容
    public static final int VOICE_CONTENT = 102;    // 语音类型内容
    public static final int POSITION_CONTENT = 103; // 位置类型内容
    public static final int IMAGE_CONTENT = 104;    // 图片类型内容

    /* 消息状态类型（2***） */
    /**
     * 客户端已发出
     */
    public static final int CLIENT_SENT = 2001;
    /**
     * 服务端已接收
     */
    public static final int SERVER_RECEIVED = 2002;
    /**
     * 服务端已发送（到对方）
     */
    public static final int SERVER_FORWARD = 2003;
    /**
     * (对方)客户端已接收
     */
    public static final int CLIENT_RECEIVED = 2004;
    /**
     * 服务端已离线存储
     */
    public static final int SERVER_STORAGE = 2005;
    /**
     * (对方)客户端拒绝接收
     */
    public static final int CLIENT_REJECT = 2006;
    /**
     * 消息已撤回
     */
    public static final int CLIENT_WITHDRAW = 2007;
    /**
     * 移动端App已发出
     */
    public static final int APP_SENT = 2008;
    /**
     * 浏览器端已发出
     */
    public static final int BROWSER_SENT = 2009;
}
