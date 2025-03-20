package com.gdgoc.baby24.controller;

import com.gdgoc.baby24.dto.GoogleTokenResponse;
import com.gdgoc.baby24.dto.GoogleUserInfo;
import com.gdgoc.baby24.dto.UpdateUserInfoRequest;
import com.gdgoc.baby24.domain.Users;
import com.gdgoc.baby24.repository.UserRepository;
import com.gdgoc.baby24.service.GoogleAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private UserRepository userRepository;

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
        try {
            // 인증 코드를 액세스 토큰으로 교환
            GoogleTokenResponse tokenResponse = googleAuthService.getToken(code);
            // 액세스 토큰을 사용해 사용자 정보 조회
            GoogleUserInfo userInfo = googleAuthService.getUserInfo(tokenResponse.getAccess_token());

            // 토큰과 사용자 정보가 정상적으로 조회된 경우 로그인 성공
            if (tokenResponse != null && userInfo != null) {
                // 추가적인 사용자 처리(예: DB 저장, JWT 생성 등)를 여기에 추가할 수 있습니다.
                return ResponseEntity.ok(Map.of(
                        "accessToken", tokenResponse.getAccess_token(),
                        "userInfo", userInfo
                ));
            } else {
                // 인증 과정 실패 시 502 상태 반환
                return ResponseEntity.status(502).body(Map.of("message", "구글 인증 실패"));
            }
        } catch (Exception e) {
            // 예외 발생 시에도 502 상태 반환
            return ResponseEntity.status(502).body(Map.of("message", "로그인 중 에러 발생", "error", e.getMessage()));
        }
    }

    // 3. 로그인 성공 후 추가 정보 입력 API
    @PostMapping("/user/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody UpdateUserInfoRequest request, Principal principal) {
        // 현재 로그인한 사용자의 이메일을 Principal에서 가져온다고 가정합니다.
        String email = principal.getName();

        // 사용자 정보 조회
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 추가 입력 받은 정보 업데이트 (예: 이름, 비상 연락망 등)
        user.setName(request.getName());
        user.setEmergencyContact(request.getEmergencyContact());
        // 필요한 다른 필드 업데이트

        // DB에 저장
        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "User info updated successfully",
                "user", user
        ));
    }
}
