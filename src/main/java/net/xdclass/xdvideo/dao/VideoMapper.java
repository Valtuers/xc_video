package net.xdclass.xdvideo.dao;

import net.xdclass.xdvideo.bean.Video;
import net.xdclass.xdvideo.dao.provider.VideoProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface VideoMapper {

    @Select("select * from video")
//    @Results({
//            @Result(column = "cover_img",property = "coverImg"),
//            @Result(column = "view_num",property = "viewNum"),
//            @Result(column = "create_time",property = "createTime")
//    })    字段映射，但太麻烦，可以直接在配置文件中配置
    List<Video> findAll();

    @Select("select * from video where id = #{id}")
    Video findById(int id);

    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video video);

    @Delete("delete form video where id = #{id}")
    int delete(int id);

    @Insert("insert into video values(null,#{title},#{summary},#{coverImg},#{viewNum},#{price}," +
            "#{createTime},#{online},#{point})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int save(Video video);
}
