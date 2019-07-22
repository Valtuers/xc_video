package net.xcvideo.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.xcvideo.bean.dto.VideoOrderDto;
import net.xcvideo.service.VideoOrderService;
import net.xcvideo.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/api/v1/order")
public class VideoOrderController {

    @Autowired
    VideoOrderService videoOrderService;

    @GetMapping("/add")
    public void saveOrderForWx(@RequestParam(value = "video_id",required = true)int video_id,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ip = IpUtils.getIpAddr(request);
        int userId = (Integer)request.getAttribute("user_id");
        VideoOrderDto videoOrderDto = new VideoOrderDto() {{
            setUserId(userId);
            setVideoId(video_id);
            setIp(ip);
        }};
        String codeUrl = videoOrderService.saveForWx(videoOrderDto);
        if(codeUrl == null){
            System.out.println("codeUrl为空");
            throw new NullPointerException();
        }
        //生成二维码图片
        try {
            Map<EncodeHintType,Object> qr_conf = new HashMap<>();
            /**
             * 二维码配置:
             *  纠错等级
             *  编码类型
             */
            qr_conf.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            qr_conf.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,qr_conf);
            ServletOutputStream out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
