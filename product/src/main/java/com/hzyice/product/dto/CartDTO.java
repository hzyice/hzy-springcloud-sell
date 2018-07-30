package com.hzyice.product.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartDTO  implements Serializable{
    private static final long serialVersionUID = 8377661316940180120L;
    // 商品id
    private String productId;

    // 购买数量
    private Integer productQuantity;


    /*
        这个坑有点深，记得以后当定义有参构造方法后，立即把无参数构造函数也写了
        不然会出错springmvc传参会出错的：
        Can not construct instance of com.hzyice.product.dto.CartDTO (no Creators, like default construct, exist):
         can not deserialize from Object value (no delegate- or property-based Creator)
     */
    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
