package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.dto.util.DtoUtils;
import com.kopivad.quizzes.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping()
    public User add(@RequestBody UserDto userDto) {
        return userService.save(DtoUtils.getUserFromDto(userDto));
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.update(id, DtoUtils.getUserFromDto(userDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
