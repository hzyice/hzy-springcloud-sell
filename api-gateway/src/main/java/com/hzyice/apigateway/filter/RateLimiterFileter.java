//package com.hzyice.apigateway.filter;
//
//import com.google.common.util.concurrent.RateLimiter;
//import com.netflix.zuul.ZuulFilter;
//
///*
//* 限流
//* 参考：http://www.cnblogs.com/shamo89/p/8940606.html
//*      https://www.cnblogs.com/LBSer/p/4083131.html
//* */
//
//public class RateLimiterFileter extends ZuulFilter {
//
//    /*常用的限流算法有两种：
//    * 1) 漏桶算法：只能稳定请求量，无法实现并发情况
//    * 2) 令牌桶算法：灵活控制请求量，解决可变并发
//    * */
//
//
//    // google 提供的开源令牌桶算法
//    private final RateLimiter rateLimiter = RateLimiter.create(100.0);
//
//
//    @Override
//    public String filterType() {
//        return null;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 0;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return false;
//    }
//
//    @Override
//    public Object run() {
//        /*
//        * 限流的逻辑部分
//        *
//        * 关于限流的方式：
//        * 1) 增加配置项，控制每个路由的限流指标，并实现动态刷新，从而实现限流
//        * 2) 基于CPU、内存、数据库等压力限流方式
//        * */
//
//        return null;
//    }
//}
