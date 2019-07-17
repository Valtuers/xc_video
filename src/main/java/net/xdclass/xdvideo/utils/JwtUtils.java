package net.xdclass.xdvideo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.xdclass.xdvideo.bean.User;

import java.util.Date;

/**
 * JWT工具类
 */
public class JwtUtils {

    private static final String SUBJECT = "xd_video";
    //过期时间
    private static final long EXPIRE = 1000*60;
    //密钥
    public static final String APPSECRET = "mc666";

    /**
     * 生成jwt
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user){
        if(user.getId()==null || user.getName()==null || user.getHeadImg()==null){
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("head_img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static Claims checkJwt(String token){
        try {
            return Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
        }catch (Exception e){

        }

        return null;
    }
}
