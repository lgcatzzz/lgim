package cn.catzzz.lgim.model;

import cn.catzzz.lgim.constant.MsgTypeConstants;
import lombok.*;

/**
 * Classname:   Message<br/>
 * Description: 消息实体<br/>
 * Date: 2022/12/24 16:30<br/>
 * Created by gql
 */
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String msgId;
    private int msgType;
    private int msgContentType;
    private String fromId;
    private String toId;
    private long timestamp;
    private int status;
    private String extend;  // 拓展字段, key/value形式, json格式存储
    private String body;    // 消息正文

    public Message buildDefaultMessage() {
        msgId = "0";
        msgType = MsgTypeConstants.LOGIN;
        msgContentType = MsgTypeConstants.NULL_CONTENT;
        fromId = String.valueOf(MsgTypeConstants.SERVER_ID);
        toId = "0";
        timestamp = 0;
        status = MsgTypeConstants.SERVER_RECEIVED;
        extend = "";
        body = "";
        return this;
    }
}
