package net.xdclass.xdvideo.dao;

import net.xdclass.xdvideo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User findById(int id);

    @Select("select * from user where openid = #{openid}")
    User findByOpenId(String openid);

    @Insert("insert into user values(null,#{openid},#{name},#{headImg},#{phone},#{sign},#{sex}," +
            "#{city},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int saveUser(User user);
}
