package com.gdgoc.baby24.service;

import com.gdgoc.baby24.converter.DeviceConverter;
import com.gdgoc.baby24.domain.Device;
import com.gdgoc.baby24.domain.User;
import com.gdgoc.baby24.dto.DeviceDTO.DeviceResponseDTO;
import com.gdgoc.baby24.repository.DeviceRepository;
import com.gdgoc.baby24.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final String STApiUrl = "https://api.smartthings.com/v1";
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private Map<String, String> generateSTHeader(String pat) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + pat);
        return headers;
    }
    public DeviceResponseDTO.DeviceListDTO getSTDevices(User user) {
        Object response = getDeviceList(user);
        return DeviceConverter.toDeviceListDTO(response);
    }

    public void addDevice(User user, String deviceIdentifier) {
        Object response = getDeviceList(user);
        Device device = DeviceConverter.toDevice(user, deviceIdentifier, response);
        deviceRepository.save(device);
    }

    private Object getDeviceList(User user) {
        String pat = user.getPAT();
        Map<String, String> headers = generateSTHeader(pat);
        WebClient webClient = WebClient.builder()
                .baseUrl(STApiUrl)
                .build();
        Object response = webClient.get()
                .uri("/devices")
                .headers(httpHeaders -> {
                    headers.forEach(httpHeaders::add);
                })
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        return response;
    }
}
