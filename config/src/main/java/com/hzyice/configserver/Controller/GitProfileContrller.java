package com.hzyice.configserver.Controller;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gti")
@RefreshScope
public class GitProfileContrller {

/*    @Value("${age}")
    private String age;*/


    @GetMapping("/value")
    public String getGitValue() {
        return "hello world";
    }


}
