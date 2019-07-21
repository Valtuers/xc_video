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
import net.xcvideo.utils.HttpUtils;
import net.xcvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
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
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveForWx(VideoOrderDto videoOrderDto) throws Exception {
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
        params.put("notify_url",weChatConfig.getNotifyUrl());
        params.put("trade_type","NATIVE");
        //生成签名
        params.put("sign",WXPayUtil.createSign(params, weChatConfig.getKey()));

        //map转xml
        String payXml = WXPayUtil.mapToXml(params);

        //统一下单
        String orderStr = HttpUtils.doPost(WeChatConfig.getUnifiedOrderUrl(), payXml, 5000);
        if(orderStr == null){
            return null;
        }

        //获取codeURL
        Map<String, String> unifiedOrderMap = WXPayUtil.xmlToMap(orderStr);
        return unifiedOrderMap.get("code_url");
    }

    @Override
    @Transactional()
    public boolean checkAndUpdateOrder(SortedMap<String,String> sortedMap) {
        String outTradeNo = sortedMap.get("out_trade_no");
        VideoOrder dbVideoOrder = videoOrderMapper.findByOutTradeNo(outTradeNo);
        if(dbVideoOrder != null && dbVideoOrder.getState() == 0){
            VideoOrder videoOrder = new VideoOrder() {{
                setOpenid(sortedMap.get("openid"));
                setOutTradeNo(outTradeNo);
                setNotifyTime(new Date());
                setState(1);
            }};
            int i = videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
            return i>0;
        }
        return false;
    }
}
