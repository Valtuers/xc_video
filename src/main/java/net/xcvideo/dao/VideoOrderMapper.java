package net.xcvideo.dao;

import net.xcvideo.bean.VideoOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单dao层
 */
@Component
public interface VideoOrderMapper {

    @Select("SELECT * FROM video_order WHERE id=#{id} and del=0")
    VideoOrder findById(int id);

    @Select("SELECT * FROM video_order WHERE out_trade_no=#{outTradeNo} and del=0")
    VideoOrder findByOutTradeNo(String outTradeNo);

    @Select("SELECT * FROM video_order WHERE user_id=#{userId}")
    List<VideoOrder> findMyOrder(int userId);

    /**
     * 保存订单
     * @param videoOrder
     * @return 主键
     */
    @Insert("INSERT INTO video_order (openid,out_trade_no,state,create_time,notify_time,total_fee,nickname,head_img,video_id,video_title,video_img,user_id,ip,del) VALUES (#{openid},#{outTradeNo},#{state},#{createTime},#{notifyTime},#{totalFee},#{nickname},#{headImg},#{videoId},#{videoTitle},#{videoImg},#{userId},#{ip},#{del})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insert(VideoOrder videoOrder);

    /**
     * 根据流水号更新支付成功的订单状态
     * @param videoOrder
     * @return
     */
    @Update("update video_order set state=#{state},notify_time=#{notifyTime},openid=#{openid} where out_trade_no=#{outTradeNo} and state=0 and del=0")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);

    /**
     * 逻辑删除订单
     * @param id
     * @param userId
     * @return 删除数量
     */
    @Update("update video_order set del=1 where id=#{id} and user_id=#{user_id}")
    int delete(@Param("id")int id,@Param("user_id")int userId);
}
