package net.xdclass.xdvideo.service.impl;

import net.xdclass.xdvideo.bean.User;
import net.xdclass.xdvideo.config.WeChatConfig;
import net.xdclass.xdvideo.dao.UserMapper;
import net.xdclass.xdvideo.service.UserService;
import net.xdclass.xdvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) throws UnsupportedEncodingException {
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),
                weChatConfig.getOpenAppId(),
                weChatConfig.getOpenAppSecret());
        //获取access_token
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if(baseMap == null || baseMap.isEmpty()){
            return null;
        }
        String accessToken = (String)baseMap.get("access_token");
        String openId = (String)baseMap.get("openid");

        User dbUser = userMapper.findByOpenId(openId);
        if(dbUser != null){
            return dbUser;
        }

        //获取用户基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);
        Map<String, Object> userMap = HttpUtils.doGet(userInfoUrl);
        User user = new User(){{
            setName(new String(((String)userMap.get("nickname")).getBytes("ISO-8859-1"),"UTF-8"));
            setSex(((Double)userMap.get("sex")).intValue());
            setHeadImg((String)userMap.get("headimgurl"));
            setCity(new String(((String)userMap.get("city")).getBytes("ISO-8859-1"),"UTF-8"));
            setOpenid((String)userMap.get("openid"));
            setCreateTime(new Date());
        }};
        userMapper.saveUser(user);
        return user;
    }
}
