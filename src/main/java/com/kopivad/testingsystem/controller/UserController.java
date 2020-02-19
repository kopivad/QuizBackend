package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.data.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable(name = "id") User user) {
        return user;
    }

    @PostMapping()
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable("id") User userFromDB, @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDB, "id");
        return userRepository.save(userFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable User user) {
        userRepository.delete(user);
    }
}
