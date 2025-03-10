package com.gdgoc.baby24.notification.service;

import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.dto.response.NotificationResponseDto;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmNotificationService implements NotificationService {

    private final FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();

    @Override
    public NotificationResponseDto send(NotificationMessageDto<String> request) throws FirebaseMessagingException {
        final Notification notification = createNotification(request.getMessage().getTitle(), request.getMessage().getBody());
        final Message message = Message.builder()
                .setToken(request.getTargetToken())
                .setNotification(notification)
                .build();

        var response = FirebaseMessaging.getInstance().send(message);
        return NotificationResponseDto.success(response);
    }

    @Override
    public List<NotificationResponseDto> sendEachForMulticast(NotificationMessageDto<List<String>> request) throws FirebaseMessagingException {
        return null;
    }

    private Notification createNotification(String title, String body) {
        return Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
    }

}
