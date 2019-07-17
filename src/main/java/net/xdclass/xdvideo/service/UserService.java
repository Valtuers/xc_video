package net.xdclass.xdvideo.service;

import net.xdclass.xdvideo.bean.User;

import java.io.UnsupportedEncodingException;

/**
 * 用户业务接口类
 */
public interface UserService {
    public User saveWeChatUser(String code) throws UnsupportedEncodingException;
}
