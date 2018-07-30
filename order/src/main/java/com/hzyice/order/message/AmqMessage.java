package com.hzyice.order.message;


import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*
* RMQ  接收方
* */

@Component
public class AmqMessage {


    // 1. 手动创建队列
   /* @RabbitListener(queues = "myQueue")
    public void printfQueues(String message) {
        System.out.println(message);
    }*/

    // 2. 发布信息时自动创建
    @RabbitListener(queuesToDeclare = @Queue("autoQueue"))
    public void printfAutoQueues(String message) {
        System.out.println("auto" + message);
    }


}
