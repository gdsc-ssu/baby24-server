package com.gdgoc.baby24.repository;

import com.gdgoc.baby24.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
