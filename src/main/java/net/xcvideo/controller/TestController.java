package net.xcvideo.controller;

import net.xcvideo.bean.JsonData;
import net.xcvideo.bean.Video;
import net.xcvideo.config.WeChatConfig;
import net.xcvideo.dao.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    WeChatConfig weChatConfig;

    @RequestMapping("/testDb")
    public Video testDb(int id){
        return videoMapper.findById(id);
    }

    @RequestMapping("/testWx")
    public JsonData testWx(){
        String appid = weChatConfig.getOpenAppId();
        System.out.println(appid);
        return JsonData.buildSuccess(appid);
    }
}
