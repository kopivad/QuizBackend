package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponseRepository extends JpaRepository<UserResponse, Long> {
}
