package com.hzyice.order.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AmqMessageTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void printfQueues() throws Exception {
        amqpTemplate.convertAndSend("autoQueue", "hello world");
    }
/*
    @Test
    public void autoQueues() throws Exception {
        amqpTemplate.convertAndSend("auto queue");
    }*/

}