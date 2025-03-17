package com.gdgoc.baby24.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
