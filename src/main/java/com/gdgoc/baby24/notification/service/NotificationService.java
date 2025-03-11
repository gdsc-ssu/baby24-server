package com.gdgoc.baby24.notification.service;

import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.dto.response.NotificationResponseDto;
import com.google.firebase.messaging.*;

import java.util.List;

public interface NotificationService {
    NotificationResponseDto send(NotificationMessageDto<String> request) throws FirebaseMessagingException;
    List<NotificationResponseDto> sendEachForMulticast(NotificationMessageDto<List<String>> request) throws FirebaseMessagingException;
}
