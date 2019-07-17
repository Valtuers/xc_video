package net.xdclass.xdvideo.controller;

import net.xdclass.xdvideo.bean.JsonData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api/v1/order")
public class OrderController {

    @GetMapping("/add")
    public JsonData saveOrder(){
        return JsonData.buildSuccess("下单成功");
    }
}
