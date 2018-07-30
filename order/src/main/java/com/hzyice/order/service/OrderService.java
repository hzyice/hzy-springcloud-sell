package com.hzyice.order.service;

import com.hzyice.order.dataobject.OrderDetail;
import com.hzyice.order.dataobject.OrderMaster;
import com.hzyice.order.dto.OrderDTO;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-12-10 16:39
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    void save(OrderMaster orderMaster);

    List<OrderDetail> findByOrderIdOrderDetailList(String orderId);

    OrderMaster findByOrderId(String orderId);
}
