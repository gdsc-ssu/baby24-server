package com.gdgoc.baby24.notification.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotificationResponseDto {

    private boolean success;

    private String messageId;

    public static NotificationResponseDto success(String messageId) {
        return new NotificationResponseDto(true, messageId);
    }

    @Override
    public String toString() {
        return "NotificationResult{" +
                "success=" + success +
                ", messageId='" + messageId + '\'' +
                '}';
    }

}
