package com.spring_cloud.eureka.client.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 빈 값 변경 시 업데이트 되도록 함
public class ProductController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${message}")
    private String message;

    @GetMapping("/product")
    public String getProduct() {
        return "Product info!!!!! From port : " + serverPort + " and message " + message;
    }

    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable String productId) {
        return "Product info!!!!! From port : " + serverPort + " and message " + message + "and productId " + productId;
    }
}
