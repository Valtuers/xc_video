package net.xcvideo.controller;

import net.xcvideo.bean.JsonData;
import net.xcvideo.bean.User;
import net.xcvideo.config.WeChatConfig;
import net.xcvideo.service.UserService;
import net.xcvideo.service.VideoOrderService;
import net.xcvideo.utils.JwtUtils;
import net.xcvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {
    @Autowired
    WeChatConfig weChatConfig;
    @Autowired
    UserService userService;
    @Autowired
    VideoOrderService videoOrderService;

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

    /**
     * 微信支付回调接口，微信以POST方式回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/order/callback")
    public void orderWxCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletInputStream in = request.getInputStream();
        //BufferedReader包装设计模式，性能更高
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = bufferedReader.readLine()) != null){
            sb.append(line);
        }
        bufferedReader.close();
        in.close();
        Map<String, String> map = WXPayUtil.xmlToMap(sb.toString());
        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(map);
        //判断签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey())){
            if("SUCCESS".equals(sortedMap.get("result_code"))){
                //更新订单状态,通知微信订单状态
                if(videoOrderService.checkAndUpdateOrder(sortedMap)){
                    response.setContentType("text/html");
                    response.getWriter().println("success");
                    return;
                }
            }
        }
        //如果订单更新失败返回fail
        response.setContentType("text/html");
        response.getWriter().println("fail");

    }
}
