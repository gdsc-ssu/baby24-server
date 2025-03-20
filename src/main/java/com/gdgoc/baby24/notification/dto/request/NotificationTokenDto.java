package com.gdgoc.baby24.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class NotificationTokenDto {

    @NotNull(message = "Token is required")
    private String token;

}
