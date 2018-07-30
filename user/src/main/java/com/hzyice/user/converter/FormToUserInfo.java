package com.hzyice.user.converter;

import com.hzyice.user.dataObject.UserInfo;
import com.hzyice.user.form.UserInforForm;

public class FormToUserInfo {

    public static UserInfo fromConverterUserInfo(UserInforForm userInforForm) {
        return  new UserInfo(userInforForm.getUsername(), userInforForm.getPassword(), userInforForm.getOpenid());
    }

}
