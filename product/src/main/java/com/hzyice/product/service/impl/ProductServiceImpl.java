package com.hzyice.product.service.impl;

import com.hzyice.product.dataobject.ProductInfo;
import com.hzyice.product.dto.CartDTO;
import com.hzyice.product.enums.ProductStatusEnum;
import com.hzyice.product.enums.ResultEnum;
import com.hzyice.product.exception.ProductException;
import com.hzyice.product.repository.ProductInfoRepository;
import com.hzyice.product.service.ProductService;
import com.hzyice.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-12-09 21:59
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfo output = new ProductInfo();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());
    }

    /*扣库存*/
    /*Transactional:事务注解 ，只能放在public方法上，因为注解原理是基于springAOP，在方法前后增强。所以要用public来修饰*/
    // @Transactional(propagation = Propagation.REQUIRES_NEW)   // 使用cglib来代理
    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<CartDTO> cartDTOS) {

        List<ProductInfo> productInfoList = new ArrayList<>();
//        int count = 0;
        for (CartDTO cartDTO : cartDTOS) {
//            count++;
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
//            if (count == 2) {
//                int i = count / 0;
//            }
//            System.out.println("count / 0出异常了...， 检验事务回滚");

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }

        /*
        * 关于业务逻辑： 发送mq消息语句不能放在，上面的for循环中，因为如果for循环第N次发生异常了，数据库的消息有Transactional
        *               可以回滚，但mq发送的消息就回滚不了了。所以切记要放在for循环之外发送mq消息
        * 关于Transactional注解事务回滚认识： Transactional修饰的方法，若被内部类调用， 则失去事务回滚的效果
        * 关于AOP使用的类型： 默认使用的是JDK代理。可以手动配置成cglib代理。JDK方法事务不支持被其它方法调用，而cglib是可以的
        *               使用cglib方式： 1）在启动类上加注解 @EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
        *                            1.1）或者在porpertions文件中    # 增加@EnableAspectJAutoProxy
                                                                        spring.aop.auto=true
                                                                        # 开启CGLIB代理
                                                                        spring.aop.proxy-target-class=true
                                       2）方法上声明 @Transactional(propagation = Propagation.REQUIRES_NEW)   // 使用cglib来代理
                                       3）内部类中调用方式 ((ProductServiceImpl) AopContext.currentProxy()).decreaseStockProcessTT(cartDTOS);
        * */
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoList));

        return productInfoList;
    }


    /*@Override
    public List<ProductInfo> decreaseStockProcess(List<CartDTO> cartDTOS) {

        List<ProductInfo> productInfoList = ((ProductServiceImpl) AopContext.currentProxy()).decreaseStockProcessTT(cartDTOS);

        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoList));

        return productInfoList;
    }*/

}
