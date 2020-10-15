package com.qmy.controller.mytest;

import com.qmy.config.rabbitMQ.HelloSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Create by qiemengyan on 2019/5/9
 */

@RestController
@RequestMapping("/rabbit")

public class RabblitMqController {
    @Autowired
    private HelloSender helloSender;
    @RequestMapping(method = RequestMethod.POST)
    public String index(){
        helloSender.send1("my name is qmy");
        return "200";
    }
}
