package net.xcvideo.dao;

import net.xcvideo.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void saveUser() {
        userMapper.saveUser(new User(){{
            setName("小葱");
            setCity("广州");
            setOpenid("asdasds");
        }});
    }
}