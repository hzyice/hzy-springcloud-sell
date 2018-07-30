package com.hzyice.apigateway.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class countContrller {

    @GetMapping("/count")
    public String coount(HttpServletRequest request) {
        System.out.println("URI= " + request.getRequestURI().toString());
        System.out.println("URL= " + request.getRequestURL());

        if (StringUtils.isEmpty(request.getParameter("token"))) {
            //throw new TokenException();
        }

        return "api-gateway controller";
    }


}
