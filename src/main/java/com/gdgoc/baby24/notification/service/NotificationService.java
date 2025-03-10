package com.gdgoc.baby24.notification.service;

import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.dto.response.NotificationResponseDto;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    NotificationResponseDto send(NotificationMessageDto<String> request) throws FirebaseMessagingException;
    List<NotificationResponseDto> sendEachForMulticast(NotificationMessageDto<List<String>> request) throws FirebaseMessagingException;
}
