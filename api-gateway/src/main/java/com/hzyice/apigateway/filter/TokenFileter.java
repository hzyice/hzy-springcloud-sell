//package com.hzyice.apigateway.filter;
//
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//
///*
//* zuul: 其内部原理是由多层fileter过滤器组成的
//* */
//
//@Component
//public class TokenFileter extends ZuulFilter{
//
////    private static final RateLimiter RATE_LIMITER = new RateLimiter() {
////        @Override
////        public double acquire() {
////            return super.acquire();
////        }
////    };
//
//    //FilterConstants
//
//    // springcloud 官方推荐的写法如下
//
//    /*
//    作用：返回一个字符串表示过滤类型，在zullk中定义了四种不同生命周期的过滤器类型。如下
//    1）pre :路由之前
//    2）routing :路由之时
//    3）post :路由之后（在route和error过滤器之后被调用）
//    4）error :发生错误调用
//     */
//    // PRE_TYPE  是FilterConstants类中的静态变量,其值越小，优先级越高
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//
//    // 要在pre之前过滤
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    // 是否过滤，改为true，表示过滤
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//
//    // 过滤逻辑实现
//    @Override
//    public Object run() {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//        HttpServletResponse response = requestContext.getResponse();
//        String token = request.getParameter("token");
//        if (StringUtils.isEmpty(token)) {
//            // 注意 对象是com.netflix.zuul.context包下的。 设置为false ，对此请求不路由
//            requestContext.setSendZuulResponse(false);
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());  // 401 未授权
//            response.setHeader("error", UUID.randomUUID().toString());
//        }
//        // uri: 是一个资源如：www.baidu.com
//        // rul: 是一个地址： www.baidu.map/sz/
//        System.out.println("URI= " + request.getRequestURI().toString());
//        System.out.println("URL= " + request.getRequestURL());
//
//        return null;
//    }
//}
