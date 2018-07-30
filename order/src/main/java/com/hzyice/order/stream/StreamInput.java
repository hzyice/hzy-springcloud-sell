package com.hzyice.order.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StreamInput {

    String INPUT = "MyStream";

    String INPUT2 = "TwoStream";

    // subscribable: 订阅   channel: 通道
    @Input(INPUT)
    SubscribableChannel input();


    @Input(INPUT2)
    SubscribableChannel input2();


/*    // message: 消息   channel: 通道
    @Output(MyStream.INPUT)
    MessageChannel output();*/
}
