package com.gdgoc.baby24.notification.fake.service;

import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.fake.FakeNotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FcmNotificationServiceTest {
    private final FakeNotificationService notificationService = new FakeNotificationService();

    @Test
    void 단일_알림을_전송한다() throws FirebaseMessagingException {
        NotificationMessageDto<String> request = new NotificationMessageDto<>(
                "test-fcm-token",
                new NotificationMessageDto.Message("test title", "test body")
        );
        notificationService.send(request);

        var result = notificationService.getSentNotifications();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).title()).isEqualTo("test title");
        assertThat(result.get(0).token()).isEqualTo("test-fcm-token");
    }

    @Test
    void 다중_알림을_전송한다() throws FirebaseMessagingException {
        NotificationMessageDto<List<String>> request = new NotificationMessageDto<>(
                List.of("test-fcm-token", "test-fcm-token2"),
                new NotificationMessageDto.Message("테스트 제목", "테스트 내용")
        );

        notificationService.sendEachForMulticast(request);

        var result = notificationService.getSentNotifications();
        assertThat(result).hasSize(2);
    }

}