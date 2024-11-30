package com.spring_cloud.eureka.client.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductClient productClient;

    public String getOrder(String orderId) {
        // orderId = 1 인 경우만 정상작동하도록 함
        if(orderId.equals("1")) {
            String productId = "2";
            String productInfo = getProductInfo(productId);
            return "Your Order is " + orderId + " and " + productInfo;
        }
        return "Not exist order...";
    }


    // 다른 서비스를 정의해둔 호출 형식대로 호출하고 결과를 받아옴
    public String getProductInfo(String productId) {
        return productClient.getProduct(productId);
    }
}
