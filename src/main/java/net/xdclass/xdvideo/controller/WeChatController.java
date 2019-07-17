package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.bean.JsonData;
import net.xdclass.xdvideo.config.WeChatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {
    @Autowired
    WeChatConfig weChatConfig;

    /**
     * 拼装微信扫一扫登陆url
     * @return
     */
    @GetMapping("/login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {
        String redirectUrl = URLEncoder.encode(weChatConfig.getOpenRedirectUrl(),"GBK");
        String qrCodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(),
                weChatConfig.getOpenAppId(),redirectUrl,accessPage);
        return JsonData.buildSuccess(qrCodeUrl);
    }
}
