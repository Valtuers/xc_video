package net.xdclass.xdvideo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.xdclass.xdvideo.bean.Video;
import net.xdclass.xdvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * 分页接口
     * @param page 当前第几页，默认第一页
     * @param size 每页大小，默认10条
     * @return
     */
    @GetMapping("/page")
    public PageInfo<Video> pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                                 @RequestParam(value = "size",defaultValue = "10")int size){
        PageHelper.startPage(page,size);
        return new PageInfo<>(videoService.findAll());
    }

    /**
     * 根据id找视频
     * @param id
     * @return
     */
    @GetMapping("/getVideo")
    public Video getVideo(@RequestParam(value = "id",required = true) int id){
        return videoService.findById(id);
    }
}
