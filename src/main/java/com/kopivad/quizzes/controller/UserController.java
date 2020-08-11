package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("all")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDto> getByEmailStartsWith(@RequestParam("email") String email) {
        return userService.getByEmailStartsWith(email);
    }

    @PostMapping
    public long save(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping
    public boolean update(@RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}
