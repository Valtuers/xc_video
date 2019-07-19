package net.xcvideo;

import io.jsonwebtoken.Claims;
import net.xcvideo.bean.User;
import net.xcvideo.utils.JwtUtils;
import org.junit.Test;

public class CommonTests {

    @Test
    public void testToken(){
        User user = new User(){{
            setId(5);
            setName("lmc");
            setHeadImg("www.mc.com");
        }};
        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
        Claims claims = JwtUtils.checkJwt(token);
        System.out.println(claims.get("id"));
        System.out.println(claims.get("name"));
        System.out.println(claims.get("head_img"));
    }
}
