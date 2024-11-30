package com.spring_cloud.eureka.client.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 서비스 호출 시 라운드 로빈 방식이 자동 적용됨
@FeignClient(name="product-service")
public interface ProductClient {

    // 다른 서비스(product-service)에 요청할 주소와 형식을 정의
    @GetMapping("/product/{id}")
    String getProduct(@PathVariable("id") String id);
}
