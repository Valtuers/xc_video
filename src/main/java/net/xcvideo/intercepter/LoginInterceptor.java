package net.xcvideo.intercepter;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import net.xcvideo.bean.JsonData;
import net.xcvideo.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Gson gson = new Gson();
    /**
     * 进入controller之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        if(token != null){
            Claims claims = JwtUtils.checkJwt(token);
            if(claims != null){
                request.setAttribute("user_id",(Integer)claims.get("id"));
                request.setAttribute("name",(String)claims.get("name"));
                return true;
            }
        }
        sendJsonMessage(response,JsonData.buildError("请登录"));
        return false;
    }

    /**
     * 响应数据给前端
     * @param response
     * @param obj
     * @throws IOException
     */
    public static void sendJsonMessage(HttpServletResponse response,Object obj) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(gson.toJson(obj));
        writer.close();
        response.flushBuffer();
    }
}
