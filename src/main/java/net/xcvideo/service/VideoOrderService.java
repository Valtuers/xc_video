package net.xcvideo.service;

import net.xcvideo.bean.dto.VideoOrderDto;

import java.util.SortedMap;

/**
 * 订单接口
 */
public interface VideoOrderService {

    String saveForWx(VideoOrderDto videoOrderDto) throws Exception;

    boolean checkAndUpdateOrder(SortedMap<String,String> sortedMap);
}
