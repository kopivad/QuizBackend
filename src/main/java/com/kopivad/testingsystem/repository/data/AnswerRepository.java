package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
