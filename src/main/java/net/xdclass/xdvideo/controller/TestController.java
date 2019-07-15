package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.bean.Video;
import net.xdclass.xdvideo.dao.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    VideoMapper videoMapper;

    @RequestMapping("/test")
    public List<Video> test(){
        return videoMapper.findAll();
    }
}
