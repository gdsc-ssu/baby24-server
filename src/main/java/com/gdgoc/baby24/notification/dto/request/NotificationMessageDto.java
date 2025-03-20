package com.gdgoc.baby24.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotificationMessageDto<T> {

    private T targetToken;

    private Message message;

    @AllArgsConstructor
    @Getter
    public static class Message {
        @NotNull(message = "Title is required")
        private String title;

        @NotNull(message = "Body is required")
        private String body;
    }

}
