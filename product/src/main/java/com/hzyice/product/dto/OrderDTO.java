package com.hzyice.product.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hzyice.product.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -4029383234976657993L;
    /** 订单id. */

    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

//    // 一个订单中可能会包含多个订单详情。所以写了DTO类。类中包含了List集合
//    private List<OrderDetail> orderDetailList = new ArrayList<>();
//
//    @JsonIgnore     // 仅限于方法上，如果放在属性上是没用的
//    public OrderStatusEnum getOrderStatusEnum() {
//        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
//    }
//
//    @JsonIgnore
//    public PayStatusEnum getPayStatusEnum() {
//        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
//    }
}
