package com.gdgoc.baby24.controller;

import com.gdgoc.baby24.dto.GoogleTokenResponse;
import com.gdgoc.baby24.dto.GoogleUserInfo;
import com.gdgoc.baby24.service.GoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    // 1. 구글 로그인 요청: 구글 인증 페이지로 리다이렉트
    @GetMapping("/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&response_type=code" +
                "&scope=" + URLEncoder.encode("openid profile email", StandardCharsets.UTF_8);
        response.sendRedirect(googleAuthUrl);
    }

    // 2. 구글 로그인 콜백: 구글에서 인증 후 호출되는 엔드포인트
    @GetMapping("/google/callback")
    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
        // 인증 코드를 액세스 토큰으로 교환
        GoogleTokenResponse tokenResponse = googleAuthService.getToken(code);
        // 액세스 토큰을 사용해 사용자 정보 조회
        GoogleUserInfo userInfo = googleAuthService.getUserInfo(tokenResponse.getAccess_token());

        // 여기서 데이터베이스에 해당 사용자가 존재하는지 확인 후 신규 가입 또는 기존 사용자 로그인 처리를 수행합니다.
        // 예를 들어, 사용자 정보를 조회한 후 JWT 토큰을 생성하고, 최종적으로 클라이언트에 토큰과 사용자 정보를 반환할 수 있습니다.
        // 이번 예제에서는 간단하게 구글에서 받은 사용자 정보와 액세스 토큰을 반환합니다.

        return ResponseEntity.ok(Map.of(
                "accessToken", tokenResponse.getAccess_token(),
                "userInfo", userInfo
        ));
    }
}
