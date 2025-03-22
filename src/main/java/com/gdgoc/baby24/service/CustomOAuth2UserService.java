package com.gdgoc.baby24.service;

import com.gdgoc.baby24.domain.User;
import com.gdgoc.baby24.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

    @Service
    public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

        private final UserRepository userRepository;

        public CustomOAuth2UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
            OAuth2User delegate = new DefaultOAuth2UserService().loadUser(userRequest);
            Map<String, Object> attributes = delegate.getAttributes();

            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String googleId = (String) attributes.get("sub");

            // 이미 있는 사용자면 중복 저장 안 하도록
//            User user = userRepository.findByEmail(email).orElse(null);
//            if (user == null) {
//                user = new User();
//                user.setEmail(email);
//                user.setUsername(name);
//                user.setGoogleId(googleId);
//                userRepository.save(user);
//            }

            User user = new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setGoogleId(googleId);
            userRepository.save(user);

            //디버깅용
            System.out.println("✅ email: " + email);
            System.out.println("✅ name: " + name);
            System.out.println("✅ googleId: " + googleId);

            return delegate;
        }
    }

