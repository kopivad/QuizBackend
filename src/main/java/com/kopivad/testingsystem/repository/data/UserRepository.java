package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
