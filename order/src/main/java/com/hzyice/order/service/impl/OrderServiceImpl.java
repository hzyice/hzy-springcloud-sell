package com.hzyice.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzyice.order.dataobject.OrderDetail;
import com.hzyice.order.dataobject.OrderMaster;
import com.hzyice.order.dataobject.ProductInfo;
import com.hzyice.order.dto.OrderDTO;
import com.hzyice.order.enums.OrderStatusEnum;
import com.hzyice.order.enums.PayStatusEnum;
import com.hzyice.order.repository.OrderDetailRepository;
import com.hzyice.order.repository.OrderMasterRepository;
import com.hzyice.order.service.OrderService;
import com.hzyice.order.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-12-10 16:44
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

   /* @Autowired
    private ProductClient productClient;*/

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

       //查询商品信息(调用商品服务)
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
        // productIdList:  是要传递的参数,此处把参数转换成json格式,封装到HttpEntity中

        ObjectMapper objectMapper = new ObjectMapper();
        String string = null;
        try {
            string = objectMapper.writeValueAsString(productIdList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpEntity<String> entity = new HttpEntity<String>(string,headers);


        ResponseEntity<List<ProductInfo>> rateResponse =
                restTemplate.exchange("http://product/listForOrder",
                        HttpMethod.POST, entity, new ParameterizedTypeReference<List<ProductInfo>>() {});

        List<ProductInfo> productInfoList = rateResponse.getBody();


        //List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);
//        List<ProductInfo> productInfoList = restTemplate.getForObject("http://PRODUCT/listForOrder", List.class);


       //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    //单价*数量
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }

       //扣库存(调用商品服务)
        //decreaseStockInputList
        List<OrderDetail> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new OrderDetail(e.getDetailId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //productClient.decreaseStock(decreaseStockInputList);
        restTemplate.getForObject("http://PRODUCT/AA", List.class);

        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }


    @Override
    public void save(OrderMaster orderMaster) {
        orderMasterRepository.save(orderMaster);
    }

    @Override
    public List<OrderDetail> findByOrderIdOrderDetailList(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderMaster findByOrderId(String orderId) {
        return orderMasterRepository.findByOrderId(orderId);
    }


}
