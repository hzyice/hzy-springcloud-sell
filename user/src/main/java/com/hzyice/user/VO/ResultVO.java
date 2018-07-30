package com.hzyice.user.VO;

/*
      code: 0,
       msg: "成功",
        data: null
   */

import com.hzyice.user.enums.UserEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 6636373948186586693L;

    private Integer code;
    private String msg;
    private T data;

    public ResultVO() {
    }

    public ResultVO(UserEnum userEnum, T data) {
        this.code = userEnum.getCode();
        this.msg = userEnum.getMsg();
        this.data = data;
    }


}
