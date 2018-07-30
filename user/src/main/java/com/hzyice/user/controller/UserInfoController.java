package com.hzyice.user.controller;


import com.hzyice.user.VO.ResultVO;
import com.hzyice.user.constant.CookieConstant;
import com.hzyice.user.constant.RedisConstant;
import com.hzyice.user.dataObject.UserInfo;
import com.hzyice.user.enums.UserEnum;
import com.hzyice.user.service.UserInfoService;
import com.hzyice.user.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/login")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;



    @GetMapping("/buyer")
    public ResultVO loginResult(String openid, HttpServletResponse response) {
        UserEnum enumValue = null;

        // 1. 去数据库查
        UserInfo userInfo = userInfoService.findByUserInfo(openid);
        if (userInfo != null) {
            // 2. 校验角色
            if (UserEnum.BUYER.getCode().equals(userInfo.getRole())) {
                // 3. 设置cookie
                CookieUtil.set(response, CookieConstant.KEY, openid, CookieConstant.EXPIRE);
                enumValue = UserEnum.SUCCEED;
            }else {
                enumValue = UserEnum.ROLE;
            }
        } else{
            enumValue = UserEnum.ERROR;
        }
        return new ResultVO<>(enumValue, null);
    }



    @GetMapping("/seller")
    public ResultVO sellerLogin(String openid, HttpServletResponse response, HttpServletRequest request) {
        UserEnum enumValue = null;
        /*
        todo
        // 逻辑不严谨,若卖家先登录,把token存入redis中,紧接着换买家登录,应该显示权限不足,但却登录成功了,因为页面中残留有买家的token,从redis中放行了
        */
        // 先去redis中查,若有数据,则返回登录成功,不去数据库查
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null ) {
            String redisKey = String.format(RedisConstant.TOKEN, cookie.getValue());
            if (!StringUtils.isEmpty(redisTemplate.opsForValue().get(redisKey))) {
                // 更新 redis中缓存时间
                redisTemplate.expire(redisKey, CookieConstant.EXPIRE, TimeUnit.SECONDS);
                // 更新客户端cookie时间
                CookieUtil.updateTime(response, cookie, CookieConstant.EXPIRE);
                return new ResultVO<>(UserEnum.SUCCEED, null);
            }
        }

        // 1. 去数据库查
        UserInfo userInfo = userInfoService.findByUserInfo(openid);
        if (userInfo != null) {
            // 2. 校验角色
            if (UserEnum.BUYER.getCode().equals(userInfo.getRole())) {
                // 3. 存入redis 切记要设置时间, 不然永久保留在内存中,除非手动删除
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                // 参数1： key 2:value 3:时间长度 4：时间单位类型
                redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN, uuid), openid, CookieConstant.EXPIRE, TimeUnit.SECONDS);

                // 4. 设置cookie
                CookieUtil.set(response, CookieConstant.TOKEN, uuid, CookieConstant.EXPIRE);
                enumValue = UserEnum.SUCCEED;
            }else {
                enumValue = UserEnum.ROLE;
            }
        } else{
            enumValue = UserEnum.ERROR;
        }
        return new ResultVO<>(enumValue, null);
    }
}
