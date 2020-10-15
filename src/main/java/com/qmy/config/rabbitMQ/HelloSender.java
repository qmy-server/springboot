package com.qmy.config.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by qiemengyan on 2019/5/9
 */
@Component
public class HelloSender {
    @Autowired
    public RabbitTemplate rabbitTemplate;

    //消息队列-1号发送者
    public void send1(String str) {
        for (int i = 0; i < 2000; i++) {
            this.rabbitTemplate.convertAndSend(QueueConstants.MESSAGE_EXCHANGE,QueueConstants.MESSAGE_ROUTE_KEY,(str+i));

        }
    }
}