package com.hzyice.user.service;

import com.hzyice.user.dataObject.UserInfo;

public interface UserInfoService {
    UserInfo findByUserInfo(String openid);
}
