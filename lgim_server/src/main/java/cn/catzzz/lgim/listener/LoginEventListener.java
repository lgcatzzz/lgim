package cn.catzzz.lgim.listener;

import cn.catzzz.lgim.model.Message;

/**
 * Classname:   LoginEventListener<br/>
 * <b>Description: 登录事件应用层回调接口</b><br/>
 * Date: 2022/12/25 19:59<br/>
 * Created by gql
 */
public interface LoginEventListener extends ServerEventListener {
    void onLogin(Message msg);
}
