package net.xcvideo.service.impl;

import net.xcvideo.bean.User;
import net.xcvideo.bean.Video;
import net.xcvideo.bean.VideoOrder;
import net.xcvideo.bean.dto.VideoOrderDto;
import net.xcvideo.config.WeChatConfig;
import net.xcvideo.dao.UserMapper;
import net.xcvideo.dao.VideoMapper;
import net.xcvideo.dao.VideoOrderMapper;
import net.xcvideo.service.VideoOrderService;
import net.xcvideo.utils.CommonUtils;
import net.xcvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    VideoOrderMapper videoOrderMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WeChatConfig weChatConfig;

    @Override
    public VideoOrder save(VideoOrderDto videoOrderDto) throws Exception {
        //查找视频信息
        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        //查找用户信息
        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder() {{
            setTotalFee(video.getPrice());
            setVideoImg(video.getCoverImg());
            setVideoTitle(video.getTitle());
            setCreateTime(new Date());
            setState(0);
            setUserId(user.getId());
            setHeadImg(user.getHeadImg());
            setNickname(user.getName());
            setDel(0);
            setIp(videoOrderDto.getIp());
            setOutTradeNo(CommonUtils.generateUUID());
            setVideoId(video.getId());
            setOpenid(user.getOpenid());
        }};
        videoOrderMapper.insert(videoOrder);
        //生成签名
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id",weChatConfig.getMchId());
        params.put("nonce_str",CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee",videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getPayCallURL());
        params.put("trade_type","NATIVE");
        String sign = WXPayUtil.createSign(params, weChatConfig.getKey());
        params.put("sign",sign);

        //map转xml
        String s = WXPayUtil.mapToXml(params);
        System.out.println(s);
        //统一下单

        //获取codeURL

        //生成二维码

        return null;
    }
}
