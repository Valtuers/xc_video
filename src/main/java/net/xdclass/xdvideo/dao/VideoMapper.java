package net.xdclass.xdvideo.dao;

import net.xdclass.xdvideo.bean.Video;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoMapper {

    @Select("select * from video")
    List<Video> findAll();
}
