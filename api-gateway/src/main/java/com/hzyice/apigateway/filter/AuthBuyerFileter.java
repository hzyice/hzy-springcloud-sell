package com.hzyice.apigateway.filter;

import com.hzyice.apigateway.constant.CookieConstant;
import com.hzyice.apigateway.util.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


/*买家过滤器, 创建订单只有买家才能访问*/

@Component
public class AuthBuyerFileter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 如果访问路径相等，则进行拦截，否则放行
        if ("/myorder/order/create".equals(request.getRequestURI())) {
            // 返回true 表示拦截， false 表示放行
            return true;
        }
        return false;
    }


    // 拦截后的业务逻辑
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 买家cookie里有openid
        Cookie cookie = CookieUtil.get(request, CookieConstant.KEY);
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            // 若cookie等于Null 或 cookie中的值等于null 则回显401状态码（权限不足）
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        return null;
    }
}
