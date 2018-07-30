package com.hzyice.order.stream;


import com.hzyice.order.dataobject.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StreamingHttpOutputMessage.class)
@EnableBinding(StreamOut.class)
public class MyStreamTest {


    @Autowired
    private StreamOut streamOut;

    @Test
    public void printfValue() {
        streamOut.output().send(MessageBuilder.withPayload("hello world").build());
    }

    @Test
    public void printfObject() {
        OrderDetail orderDetail = new OrderDetail("123", 456);
        streamOut.output().send(MessageBuilder.withPayload(orderDetail).build());
    }



}