package com.gdgoc.baby24.repository;

import com.gdgoc.baby24.domain.Device;
import com.gdgoc.baby24.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUser(User user);

    @Query("select d from Device d where d.user = :user and d.alert = true")
    List<Device> findAlertDeviceByUser(@Param("user")User user);

}
