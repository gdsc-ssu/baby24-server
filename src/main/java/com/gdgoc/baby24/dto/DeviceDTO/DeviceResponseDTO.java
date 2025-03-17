package com.gdgoc.baby24.dto.DeviceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class DeviceResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceDTO {
        String deviceIdentifier;
        String category;
        String name;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceListDTO {
        List<DeviceDTO> deviceDTOList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceStatusDTO {
        Long deviceId;
        String name;
        String type;
        String status;
        Boolean alert;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceStatusListDTO {
        List<DeviceStatusDTO> deviceStatusDTOList;
    }
}
