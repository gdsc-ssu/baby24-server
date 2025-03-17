package com.gdgoc.baby24.controller;

import com.gdgoc.baby24.common.exception.BasicResponse;
import com.gdgoc.baby24.domain.User;
import com.gdgoc.baby24.dto.DeviceDTO.DeviceRequestDTO;
import com.gdgoc.baby24.dto.DeviceDTO.DeviceResponseDTO;
import com.gdgoc.baby24.repository.UserRepository;
import com.gdgoc.baby24.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;
    private final UserRepository userRepository;
    //TODO: userId 추후에 토큰방식으로 변경
    @GetMapping("/{userId}")
    public ResponseEntity<BasicResponse> getDevicesByST(@PathVariable Long userId) {
        //TODO: 로그인 구현 후 제거
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        DeviceResponseDTO.DeviceListDTO deviceListDTO = deviceService.getSTDevices(user);
        BasicResponse response = new BasicResponse(200, "SmartThings 기기 목록 조회 성공", deviceListDTO);
        return response.ok(deviceListDTO);
    }
    @PostMapping("/{userId}")
    public ResponseEntity<BasicResponse> addDevice(@PathVariable Long userId, @RequestBody DeviceRequestDTO.DeviceIdentifierDTO request) {
        //TODO: 로그인 구현 후 제거
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));
        deviceService.addDevice(user, request.getDeviceIdentifier());
        return new BasicResponse().noContent();
    }
}
