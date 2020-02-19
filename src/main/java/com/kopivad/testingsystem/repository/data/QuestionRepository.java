package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
