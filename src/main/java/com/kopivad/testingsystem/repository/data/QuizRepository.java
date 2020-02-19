package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
