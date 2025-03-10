package com.gdgoc.baby24.notification.controller;

import com.gdgoc.baby24.common.dto.ResponseDto;
import com.gdgoc.baby24.notification.dto.request.NotificationMessageDto;
import com.gdgoc.baby24.notification.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/single")
    public ResponseEntity<Void> send(
            @Valid @RequestBody NotificationMessageDto<String> requestDto
    ) throws FirebaseMessagingException {
        notificationService.send(requestDto);
        return ResponseDto.noContent();
    }

    @PostMapping("/multi")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> sendEachForMulticast(
            @Valid @RequestBody final NotificationMessageDto<List<String>> requestDto
    ) throws FirebaseMessagingException {
        notificationService.sendEachForMulticast(requestDto);
        return ResponseDto.noContent();
    }

}
