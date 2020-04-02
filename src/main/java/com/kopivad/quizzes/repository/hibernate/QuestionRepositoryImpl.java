package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("questionHibernateRepository")
@AllArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return entityManager.createQuery("select q from Question q", Question.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return entityManager.find(Question.class, id);
    }

    @Override
    @Transactional
    public Question save(Question question) {
        entityManager.persist(question);
        return question;
    }

    @Override
    @Transactional
    public Question update(Long id, Question question) {
        Question questionForUpdate = entityManager.find(Question.class, id);
        BeanUtils.copyProperties(question, questionForUpdate, "id");
        return questionForUpdate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Question questionForRemove = entityManager.find(Question.class, id);
        entityManager.remove(questionForRemove);
    }
}
