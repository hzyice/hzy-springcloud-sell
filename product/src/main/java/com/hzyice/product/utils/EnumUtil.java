package com.hzyice.product.utils;

/*
枚举工具类
通过传过来枚举值。得到枚举对象
 */

import com.hzyice.product.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer va, Class<T> enumClass){
        for (T t : enumClass.getEnumConstants()) {
            if (t.getCode().equals(va)) {
                return t;
            }
        }
        return null;
    }

}
