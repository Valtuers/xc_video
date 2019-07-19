package net.xcvideo.controller;

import net.xcvideo.bean.JsonData;
import net.xcvideo.bean.User;
import net.xcvideo.config.WeChatConfig;
import net.xcvideo.service.UserService;
import net.xcvideo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {
    @Autowired
    WeChatConfig weChatConfig;
    @Autowired
    UserService userService;

    /**
     * 拼装微信扫一扫登陆url
     * @return
     */
    @GetMapping("/login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {
        String redirectUrl = URLEncoder.encode(weChatConfig.getOpenRedirectUrl(),"GBK");
        String qrCodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppId(),redirectUrl,accessPage);
        return JsonData.buildSuccess(qrCodeUrl);
    }

    /**
     * 微信扫码登录回调地址
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
    @GetMapping("/user/callback")
    public void weChatUserCallBack(@RequestParam(value = "code",required = true) String code,
                                   String state,HttpServletResponse response) throws IOException {
        User user = userService.saveWeChatUser(code);
        if(user != null){
            //生成JWT
            String token = JwtUtils.geneJsonWebToken(user);
            //state:需要当前用户拼接http:// 这样才不会站内跳转
            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));
        }
    }
}
