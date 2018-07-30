package com.hzyice.order.stream;


import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StreamOut {

    @Output(StreamInput.INPUT)
    MessageChannel output();



    @Output(StreamInput.INPUT2)
    MessageChannel output2();
}
