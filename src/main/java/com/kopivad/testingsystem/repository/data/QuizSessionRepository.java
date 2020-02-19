package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {
}
