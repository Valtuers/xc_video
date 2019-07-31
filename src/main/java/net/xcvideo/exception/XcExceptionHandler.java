package net.xcvideo.exception;

import net.xcvideo.bean.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理控制器
 */
@ControllerAdvice
public class XcExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){
        if(e instanceof XcException){
            XcException xcException = (XcException) e;
            return JsonData.buildError(xcException.getMsg(),xcException.getCode());
        }else{
            return JsonData.buildError("全局异常，未知错误");
        }
    }
}