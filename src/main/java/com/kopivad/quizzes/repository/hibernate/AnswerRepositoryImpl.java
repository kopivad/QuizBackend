package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("answerHibernateRepository")
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Answer> findAll() {
        return entityManager.createQuery("select a from Answer a", Answer.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Answer findById(Long id) {
        return entityManager.find(Answer.class, id);
    }

    @Override
    @Transactional
    public Answer save(Answer answer) {
        entityManager.persist(answer);
        return answer;
    }

    @Override
    @Transactional
    public Answer update(Long id, Answer answer) {
        Answer answerForUpdate = entityManager.find(Answer.class, id);
        BeanUtils.copyProperties(answer, answerForUpdate, "id");
        return answerForUpdate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Answer answerForRemove = entityManager.find(Answer.class, id);
        entityManager.remove(answerForRemove);
    }
}
