package com.hzyice.order.enums;

import lombok.Getter;

/**
 * Created by 廖师兄
 * 2017-12-10 17:32
 */
@Getter
public enum ResultEnum {
    PARAM_ERROR(1, "参数错误"),
    CART_EMPTY(2, "购物车为空"),
    IS_NULL(3, "订单不存在"),
    SATAE(4, "订单状态有误"),
    DETAILS_IS_NOTNULL(5, "订单详情不能为空")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
