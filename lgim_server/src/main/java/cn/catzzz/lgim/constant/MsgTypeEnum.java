package cn.catzzz.lgim.constant;

/**
 * Classname:   MsgTypeEnum<br/>
 * Description: <br/>
 * Date: 2022/12/24 14:25<br/>
 * Created by gql
 */
public enum MsgTypeEnum {
    LOGIN(1001, "登录"),
    HEARTBEAT(1002, "心跳");

    MsgTypeEnum(int code, String name) {
    }
}
