package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("hibernateRepository")
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    @Override
    public User update(Long id, User user) {
        User userForUpdate = entityManager.find(User.class, id);
        BeanUtils.copyProperties(user, userForUpdate, "id", "creationDate");
        return entityManager.merge(userForUpdate);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User userForDelete = entityManager.find(User.class, id);
        entityManager.remove(userForDelete);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return entityManager.find(User.class, email);
    }
}
