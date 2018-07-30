package com.hzyice.order.stream;


import com.hzyice.order.dataobject.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(value = {StreamInput.class})
public class SinkReceiver {

    private Logger log = LoggerFactory.getLogger(SinkReceiver.class);


    @StreamListener(StreamInput.INPUT)
    //SendTo： 当消息接收后，把接收到的信号发送出去。 return: 就是响应的内容
    @SendTo(StreamInput.INPUT2)
    public String printfValue(OrderDetail mes) {
        //log.info(mes);
        System.out.println("INPUT： " + mes.toString());
        return "input消费完了...";
    }


    @StreamListener(StreamInput.INPUT2)
    public void printfString(String mes) {
        System.out.println("INPUT2: " + mes);
    }


    public static void main(String[] args) {
        int i = 1;

        int a;

        try {
            a = i / 0;
        } catch (Exception e) {
            System.out.println("666");
            e.printStackTrace();
        }

        System.out.println("虽然抛出了异常，但还是执行了...");

    }

}
