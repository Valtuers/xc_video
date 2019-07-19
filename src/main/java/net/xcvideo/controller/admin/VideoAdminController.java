package net.xcvideo.controller.admin;


import net.xcvideo.bean.Video;
import net.xcvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/video")
public class VideoAdminController {
    @Autowired
    VideoService videoService;


    @PutMapping("/updateVideo")
    public int updateVideo(@RequestBody Video video){
        return videoService.update(video);
    }

    /**
     * 根据id删除视频
     * @param id
     * @return
     */
    @DeleteMapping("/delVideo")
    public int delVideo(@RequestParam(value = "id",required = true) int id){
        return videoService.delete(id);
    }

    @PostMapping("/addVideo")
    public int addVideo(@RequestBody Video video){
        int row = videoService.add(video);
        System.out.println(video.getId());
        return row;
    }
}
