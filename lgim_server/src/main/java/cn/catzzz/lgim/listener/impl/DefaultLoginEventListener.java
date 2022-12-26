package cn.catzzz.lgim.listener.impl;

import cn.catzzz.lgim.listener.LoginEventListener;
import cn.catzzz.lgim.model.Message;

/**
 * Classname:   DefaultLoginEventListener<br/>
 * <b>Description: 登录业务的默认实现</b><br/>
 * Date: 2022/12/25 20:07<br/>
 * Created by gql
 */
public class DefaultLoginEventListener implements LoginEventListener {
    @Override
    public void onLogin(Message msg) {
        System.out.println("---登录逻辑处理---");
    }
}
