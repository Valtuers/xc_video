package net.xcvideo.controller;

import net.xcvideo.bean.JsonData;
import net.xcvideo.bean.dto.VideoOrderDto;
import net.xcvideo.service.VideoOrderService;
import net.xcvideo.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    VideoOrderService videoOrderService;

    @GetMapping("/add")
    public JsonData saveOrder(@RequestParam(value = "video_id",required = true)int video_id,
                              HttpServletRequest request) throws Exception {
        String ip = IpUtils.getIpAddr(request);
        int userId = 1;
        VideoOrderDto videoOrderDto = new VideoOrderDto() {{
            setUserId(userId);
            setVideoId(video_id);
            setIp(ip);
        }};
        videoOrderService.save(videoOrderDto);
        return JsonData.buildSuccess("下单成功");
    }
}
