package com.hzyice.product.controller;

import com.hzyice.product.VO.ProductInfoVO;
import com.hzyice.product.VO.ProductVO;
import com.hzyice.product.VO.ResultVO;
import com.hzyice.product.dataobject.ProductCategory;
import com.hzyice.product.dataobject.ProductInfo;
import com.hzyice.product.dto.CartDTO;
import com.hzyice.product.service.CategoryService;
import com.hzyice.product.service.ProductService;
import com.hzyice.product.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品
 * Created by 廖师兄
 * 2017-12-09 21:13
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 1. 查询所有在架的商品
     * 2. 获取类目type列表
     * 3. 查询类目
     * 4. 构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list() {
        //1. 查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //2. 获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        //3. 从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //4. 构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory: categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }


    // 提供给外调用的接口
    /**
     * 获取商品列表(给订单服务用的)
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {

        // 测试 hystrix  远程调用outTime时间，是否会触发服务降级
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return productService.findList(productIdList);
    }


    // 扣库存
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> decreaseStockInputList) {
        productService.decreaseStockProcess(decreaseStockInputList);
    }


    @PostMapping("/test")
    public void aa(@RequestBody List<String> aa) {
        for (String s : aa) {
            System.out.println(s);
        }
    }
}
