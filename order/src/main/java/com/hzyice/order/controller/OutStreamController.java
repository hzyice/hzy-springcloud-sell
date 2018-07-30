package com.hzyice.order.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stream")
//@EnableBinding(value = {StreamOut.class, StreamingHttpOutputMessage.class})
public class OutStreamController {

//    @Autowired
//    private StreamOut streamOut;

    private Logger logger = LoggerFactory.getLogger(OutStreamController.class);

    /*

    * */
    @GetMapping("/outValue")
    public void printfValue(){
//        streamOut.output().send(MessageBuilder.withPayload("hello world").build());
    }





}
