package com.gdgoc.baby24.repository;

import com.gdgoc.baby24.domain.Device;
import com.gdgoc.baby24.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUser(Users user);
}
