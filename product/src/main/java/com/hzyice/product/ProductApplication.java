package com.hzyice.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//来解决用JDK方法内部类调用被Transactional修饰的方法事务无效现象
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableDiscoveryClient
@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}
}
