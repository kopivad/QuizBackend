package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizResultsRepository extends JpaRepository<QuizResult, Long> {
}
