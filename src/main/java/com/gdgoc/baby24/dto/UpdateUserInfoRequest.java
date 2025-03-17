package com.gdgoc.baby24.dto;

public class UpdateUserInfoRequest {
    private String name;
    private String emergencyContact;
    // 필요 시 다른 필드 추가

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
