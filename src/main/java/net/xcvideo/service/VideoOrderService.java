package net.xcvideo.service;

import net.xcvideo.bean.VideoOrder;
import net.xcvideo.bean.dto.VideoOrderDto;

/**
 * 订单接口
 */
public interface VideoOrderService {

    VideoOrder save(VideoOrderDto videoOrderDto) throws Exception;
}
