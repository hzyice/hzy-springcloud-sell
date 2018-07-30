package com.hzyice.order.message;


import com.fasterxml.jackson.core.type.TypeReference;
import com.hzyice.order.dataobject.ProductInfo;
import com.hzyice.order.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInfoReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(ProductInfoReceiver.class);

    /*redis中的KEY  . 后面加个%s是字符串拼接，把商品的id作为后缀*/
    private static String REDIS_PRODUCT_INFO_KEY = "productInfo%s";


    /*queuesToDeclare: 自动创建消息队列*/
    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void printf(String productInfoJson) {
        logger.info(" @Queue(productInfo)= {}", productInfoJson);
        /*把rabbitmq传来的json数据转换成对象。 注意rabbitmq 消息传送不能直接传对象（即使反序列化也不行）*/
        //ProductInfo productInfo = (ProductInfo) JsonUtil.fromJson(productInfoJson, ProductInfo.class);

        List<ProductInfo> productInfoList  = (List<ProductInfo>) JsonUtil.fromJsonToList(productInfoJson, new TypeReference<List<ProductInfo>>() {
        });

        for (ProductInfo productInfo : productInfoList) {
            /*存储到redis中*/
            stringRedisTemplate.opsForValue().set(String.format(REDIS_PRODUCT_INFO_KEY, productInfo.getProductId()),
                    String.valueOf(productInfo.getProductStock()));
        }

    }


}
