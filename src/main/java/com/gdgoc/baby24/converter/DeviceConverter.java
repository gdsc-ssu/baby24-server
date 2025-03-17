package com.gdgoc.baby24.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdgoc.baby24.domain.Device;
import com.gdgoc.baby24.domain.User;
import com.gdgoc.baby24.dto.DeviceDTO.DeviceResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class DeviceConverter {
    public static DeviceResponseDTO.DeviceListDTO toDeviceListDTO(Object response) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.valueToTree(response);

        List<DeviceResponseDTO.DeviceDTO> deviceList = new ArrayList<>();
        for (JsonNode item : rootNode.get("items")) {
            String deviceId = item.get("deviceId").asText();
            String name = item.get("name").asText();
            String category = "";
            JsonNode componentsNode = item.get("components");
            JsonNode categoriesNode = componentsNode.get(0).get("categories");
            category = categoriesNode.get(0).get("name").asText();

            DeviceResponseDTO.DeviceDTO deviceDTO = DeviceResponseDTO.DeviceDTO.builder()
                    .deviceIdentifier(deviceId)
                    .category(category)
                    .name(name)
                    .build();

            deviceList.add(deviceDTO);
        }
        return DeviceResponseDTO.DeviceListDTO.builder()
                .deviceDTOList(deviceList)
                .build();
    }

    public static Device toDevice(User user, String identifier, Object response) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.valueToTree(response);
        String name = null;
        String category = "";
        for (JsonNode item : rootNode.get("items")) {
            String deviceId = item.get("deviceId").asText();
            if (deviceId.equals(identifier)) {
                name = item.get("name").asText();
                JsonNode componentsNode = item.get("components");
                JsonNode categoriesNode = componentsNode.get(0).get("categories");
                category = categoriesNode.get(0).get("name").asText();
            }
        }
        return Device.builder()
                .user(user)
                .identifier(identifier)
                .name(name)
                .alert(false)
                .type(category)
                .build();
    }

    public static DeviceResponseDTO.DeviceStatusListDTO toDeviceStatusListDTO(
            List<DeviceResponseDTO.DeviceStatusDTO> deviceStatusDTOList) {
        return DeviceResponseDTO.DeviceStatusListDTO.builder()
                .deviceStatusDTOList(deviceStatusDTOList)
                .build();
    }
    public static DeviceResponseDTO.DeviceStatusDTO toDeviceStatusDTO(Device device, String status) {
        return DeviceResponseDTO.DeviceStatusDTO.builder()
                .deviceId(device.getId())
                .name(device.getName())
                .type(device.getType())
                .status(status)
                .build();
    }


}
