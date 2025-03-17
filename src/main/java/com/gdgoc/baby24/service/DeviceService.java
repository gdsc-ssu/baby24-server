package com.gdgoc.baby24.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgoc.baby24.common.exception.BusinessException;
import com.gdgoc.baby24.common.exception.ErrorCode;
import com.gdgoc.baby24.common.exception.NotFoundException;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final String STApiUrl = "https://api.smartthings.com/v1";
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    public DeviceResponseDTO.DeviceStatusListDTO getDeviceStatusList(User user) {
        List<Device> deviceList = deviceRepository.findAllByUser(user);

        List<DeviceResponseDTO.DeviceStatusDTO> statusDTOList = deviceList.stream()
                .map(device -> {
                    String status = getDeviceStatus(device);
                    return DeviceConverter.toDeviceStatusDTO(device, status);
                })
                .collect(Collectors.toList());

        return DeviceConverter.toDeviceStatusListDTO(statusDTOList);
    }
    public DeviceResponseDTO.DeviceListDTO getDevices(User user) {
        Object response = getDeviceList(user);
        return DeviceConverter.toDeviceListDTO(response);
    }
    private Map<String, String> generateHeader(String personalAccessToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + personalAccessToken);
        return headers;
    }
    public String getDeviceStatus(Device device) {
        User user = userRepository.findById(device.getUser().getId())
                .orElseThrow(()->new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        Map<String, String> headers = generateHeader(user.getPersonalAccessToken());
        WebClient webClient = WebClient.builder()
                .baseUrl(STApiUrl)
                .build();
        Object response = webClient.get()
                .uri("/devices/"+device.getIdentifier()+"/components/main/capabilities/switch/status")
                .headers(httpHeaders -> {
                    headers.forEach(httpHeaders::add);
                })
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.valueToTree(response);
        String status = rootNode.get("switch").get("value").asText();
        return status;
    }

    public void addDevice(User user, String deviceIdentifier) {
        Object response = getDeviceList(user);
        Device device = DeviceConverter.toDevice(user, deviceIdentifier, response);
        if(!device.getType().equals("Light")) throw new BusinessException(ErrorCode.DEVICE_NOT_ALLOWED);
        deviceRepository.save(device);
    }

    public void setDeviceAlert(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        if(!device.getType().equals("Light")) throw new BusinessException(ErrorCode.DEVICE_NOT_ALLOWED);
        device.setAlert(true);
        deviceRepository.save(device);
    }
    public void removeDeviceAlert(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));
        device.setAlert(false);
        deviceRepository.save(device);
    }

    private Object getDeviceList(User user) {
        String personalAccessToken = user.getPersonalAccessToken();
        Map<String, String> headers = generateHeader(personalAccessToken);
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
