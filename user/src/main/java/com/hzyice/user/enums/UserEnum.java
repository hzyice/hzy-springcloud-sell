package com.hzyice.user.enums;

import lombok.Getter;

@Getter
public enum UserEnum {
    SUCCEED(0, "成功"),
    ERROR(1, "登录失败"),
    ROLE(2, "角色权限有误"),
    BUYER(1, "买家角色"),
    SELLER(2, "卖家角色"),
    ;

    private Integer code;
    private String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
