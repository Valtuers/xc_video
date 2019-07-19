package net.xcvideo.service;

import net.xcvideo.bean.Video;

import java.util.List;

/**
 * 视频业务接口
 */
public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video video);

    int delete(int id);

    int add(Video video);
}
