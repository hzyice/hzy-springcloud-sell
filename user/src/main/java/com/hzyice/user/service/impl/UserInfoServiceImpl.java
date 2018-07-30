package com.hzyice.user.service.impl;

import com.hzyice.user.dataObject.UserInfo;
import com.hzyice.user.repository.UserInfoRepository;
import com.hzyice.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl implements UserInfoService {
    
    @Autowired
    private UserInfoRepository userInfoRepository;
    
    
    @Override
    public UserInfo findByUserInfo(String openid) {
        return userInfoRepository.findByOpenid(openid);

//        UserInfo userInfos = new UserInfo();
//        try {
//            // 两个ojbect不要求是同一个类下的对象，只要object1中的属性名和属性类型 与 object2中一致。就会拷贝过去。
//            BeanUtils.copyProperties(object, userInfos);
//            // 在jdk1.8中使用springJPA 中的findOne 方法的语法有变（根据对象查），正确如下：
//            Example<UserInfo> example = Example.of(userInfos);
//            Optional<UserInfo> one = userInfoRepository.findOne(example);
//            if (one.isPresent()) {
//                userInfos = one.get();
//            }
//        } catch (Exception e) {
//            System.out.println("参数 userinfo 异常...");
//            return null;
//        }
    }
}
