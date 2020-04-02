package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@AllArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Quiz> findAll() {
        return entityManager.createQuery("select q from Quiz q", Quiz.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Quiz findById(Long id) {
        return entityManager.find(Quiz.class, id);
    }

    @Override
    @Transactional
    public Quiz save(Quiz quiz) {
        entityManager.persist(quiz);
        return quiz;
    }

    @Override
    @Transactional
    public Quiz update(Long id, Quiz quiz) {
        Quiz quizForUpdate = entityManager.find(Quiz.class, id);
        BeanUtils.copyProperties(quiz, quizForUpdate, "id", "creationDate");
        return quizForUpdate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Quiz quizForRemove = entityManager.find(Quiz.class, id);
        entityManager.remove(quizForRemove);
    }
}
