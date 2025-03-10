package com.gdgoc.baby24.notification.fake;

import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.dto.response.NotificationResponseDto;
import com.gdgoc.baby24.notification.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FakeNotificationService implements NotificationService {
    private final List<NotificationRecord> sentNotifications = new ArrayList<>();

    @Override
    public NotificationResponseDto send(NotificationMessageDto<String> request) throws FirebaseMessagingException {
        String messageId = generateMessageId();
        sentNotifications.add(new NotificationRecord(
                request.getMessage().getTitle(),
                request.getMessage().getBody(),
                request.getTargetToken(),
                messageId
        ));
        return NotificationResponseDto.success(messageId);
    }

    @Override
    public List<NotificationResponseDto> sendEachForMulticast(NotificationMessageDto<List<String>> request) throws FirebaseMessagingException {
        return null;
    }

    public List<NotificationRecord> getSentNotifications() {
        return new ArrayList<>(sentNotifications);
    }

    private String generateMessageId() {
        return "fake-message-" + UUID.randomUUID();
    }

    // 알림 기록을 위한 레코드
    public record NotificationRecord(
            String title,
            String body,
            String token,
            String messageId
    ) {
    }

}
