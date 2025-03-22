//package com.gdgoc.baby24.service;
//
//import com.gdgoc.baby24.dto.GoogleTokenResponse;
//import com.gdgoc.baby24.dto.GoogleUserInfo;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class GoogleAuthService {
//
//    @Value("${google.client.id}")
//    private String clientId;
//
//    @Value("${google.client.secret}")
//    private String clientSecret;
//
//    @Value("${google.redirect.uri}")
//    private String redirectUri;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    // 토큰 교환: 인증 코드로 액세스 토큰을 요청
//    public GoogleTokenResponse getToken(String code) {
//        String tokenEndpoint = "https://oauth2.googleapis.com/token";
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", code);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
//        ResponseEntity<GoogleTokenResponse> response = restTemplate.postForEntity(tokenEndpoint, request, GoogleTokenResponse.class);
//        return response.getBody();
//    }
//
//    // 사용자 정보 조회: 액세스 토큰으로 사용자 정보를 요청
//    public GoogleUserInfo getUserInfo(String accessToken) {
//        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, entity, GoogleUserInfo.class);
//        return response.getBody();
//    }
//}
