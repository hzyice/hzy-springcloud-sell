package com.hzyice.order.controller;

import com.hzyice.order.VO.ResultVO;
import com.hzyice.order.converter.OrderForm2OrderDTOConverter;
import com.hzyice.order.dataobject.OrderDetail;
import com.hzyice.order.dataobject.OrderMaster;
import com.hzyice.order.dto.OrderDTO;
import com.hzyice.order.enums.OrderStatusEnum;
import com.hzyice.order.enums.ResultEnum;
import com.hzyice.order.exception.OrderException;
import com.hzyice.order.form.OrderForm;
import com.hzyice.order.service.OrderService;
import com.hzyice.order.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-12-10 16:36
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            //log.error("【创建订单】参数不正确, orderForm={}" + orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            //log.error("【创建订单】购物车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }


    /*
    完订单
    */
    @PostMapping("/finish")
    public OrderDTO finishOrder(String orderId) {
        // 1. 查询订单
        OrderMaster orderMaster = orderService.findByOrderId(orderId);
        if (orderMaster == null) {
            // 订单不存在
            throw new OrderException(ResultEnum.IS_NULL);
        }
        // 2. 查看订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            // 订单状态不是新订单
            throw new OrderException(ResultEnum.SATAE);
        }

        // 3. 完结订单
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderService.save(orderMaster);

        // 4. 判断订单详情
        List<OrderDetail> orderDetails = orderService.findByOrderIdOrderDetailList(orderMaster.getOrderId());
        if (CollectionUtils.isEmpty(orderDetails)) {
            // 订单详情不能为Null
            throw new OrderException(ResultEnum.DETAILS_IS_NOTNULL);
        }

        // 5. 填充回显数据
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }



    @PostMapping("/testZuul")
    public void printf(HttpServletRequest request) {
        System.out.println("testZuul...");
    }
}
