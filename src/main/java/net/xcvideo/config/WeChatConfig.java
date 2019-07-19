package net.xcvideo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 微信配置类
 */
@Configuration
@PropertySource("classpath:application.yml")
public class WeChatConfig {

    @Value("${wxpay.appid}")
    private String appId;

    @Value("${wxpay.appsecret}")
    private String appSecret;

    /**
     * 开放平台
     */
    @Value("${wxopen.appid}")
    private String openAppId;

    @Value("${wxopen.appsecret}")
    private String openAppSecret;

    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;
    /**
     * 微信开放平台二维码连接
     */
    private static final String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
    /**
     * 开放平台获取access_token地址
     */
    private static final String OPEN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 开放平台获取用户信息地址
     */
    private static final String OPEN_USER_INFO_URL = "https://api.weixin.qq" +
            ".com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenAppSecret() {
        return openAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        this.openAppSecret = openAppSecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getOpenUserInfoUrl() {
        return OPEN_USER_INFO_URL;
    }
}
