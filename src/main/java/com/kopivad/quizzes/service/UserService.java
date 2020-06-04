package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.UserForm;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User save(UserForm userForm);
    User update(Long id, UserForm userForm);
    void delete(Long id);
}
