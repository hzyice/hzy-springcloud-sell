package com.hzyice.order.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/git")
public class GitProfileController {

    //@Value("${age}")
    private String age;

    @GetMapping("/value")
    public String printfGitValue() {
        return age;
    }


}
