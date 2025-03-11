package com.gdgoc.baby24.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping({"/", "/health"})
    public String health() {
        return "Application Health Good!";
    }

}
