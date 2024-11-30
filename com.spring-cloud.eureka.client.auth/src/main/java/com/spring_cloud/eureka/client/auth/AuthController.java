package com.spring_cloud.eureka.client.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/sign-in") // 원래는 post 로 해야 적절
    public ResponseEntity<?> createAuthToken(@RequestParam String userId) {
        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(userId)));
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String accessToken;
    }
}
