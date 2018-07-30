package com.hzyice.order.controller;


import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
// hystrix之---指定当前类中全局降级处理
@DefaultProperties(defaultFallback = "defaultEvent")
public class HystrixController {

    // todo
    // 自动注入会报错（cglib代理）， 方法中new却能行
//    @Autowired
//    private RestTemplate restTemplate;

    // 固定在方法上加降级处理方法
//    @HystrixCommand(fallbackMethod = "errorFallbackMethodd")

    /*
    服务熔断 重要的四个配置
    circuitBreaker.enabled
    circuitBreaker.requestVolumeThreshold       value = 10      // 1）10秒内的请求次数
    circuitBreaker.errorThresholdPercentage     value = 60      // 2）60的百分比
    circuitBreaker.sleepWindowInMilliseconds    value = 10000   // 3）10秒
    语言及执行流程如下：
    如果1）即10秒内请求失败次数比例达到2）即%60.则开启断路，主逻辑进入休眠状态，然后等待3）即10秒后，然后开启半熔断状态，在半熔断状态下若请求成功，
    则关闭断路器恢复正常，否则开启断路，返回到3）再等待10秒(实际不用等10秒那么久,在hystrix监控页面上，当失败百分比退回0时，就开启半熔断阙状态)，再进入半熔断阙状态依次循环。
     */

    // 超时设置的 key 在HystrixCommandProperties类中找
    @HystrixCommand(commandProperties =
            {
                    //@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            })
    @GetMapping("/getHystrix")
    public String count(Integer value) {
        // 服务降级 只要是方法中（无论远程通信还是代码异常）出现了异常，都会调用 fallbackMethod 指定的方法。
        if (value % 2 == 0){
            return "succees";
        }
        // 本应该把sleuth调用链路输出到zipkin上，这里用的是restTemplate组件来通信，只显示了order项目的调用信息，没
        // 显示product那方的调用信息。imocc中使用的是feign组件。调用链路显示齐全， todo 待确定 sleuth不支持restTemplate
        RestTemplate restTemplate = new RestTemplate();
        List count = restTemplate.postForObject("http://localhost:8762/product/listForOrder",
                Arrays.asList("1"), List.class);
        return count.toString();
    }


    private String errorFallbackMethodd(){
        return "\"http://localhost:8762/product/listForOrder\" is NG...";
    }

    private String defaultEvent() {
        return "defaultEvent is NG...";
    }

}
