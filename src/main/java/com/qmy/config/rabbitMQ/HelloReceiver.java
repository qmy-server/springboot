package com.qmy.config.rabbitMQ;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by qiemengyan on 2019/5/9
 */
@Component
public class HelloReceiver {
    private List<String> list = new LinkedList<>();  //用于存放待处理的数据
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(7, 8, 5,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    //使用时，放开该注解
    //@RabbitListener(queues = QueueConstants.MESSAGE_QUEUE_NAME)
    public void processMessage(Channel channel, Message message) {
        try {
            if (list.size() == 1000) {
                System.out.println("满1000的消息处理");
                pool.execute(new MyDataProcess(list));
                list.clear();
                System.out.println("清空了List");
            } else {
                list.add(message.getBody().toString());
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
