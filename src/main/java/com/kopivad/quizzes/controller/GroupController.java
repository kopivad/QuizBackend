package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public long create(@RequestBody GroupDto dto) {
        return groupService.create(dto);
    }

    @PutMapping
    public boolean update(@RequestBody GroupDto dto) {
        return groupService.update(dto);
    }

    @GetMapping("all")
    public List<GroupDto> getAll() {
        return groupService.getAll();
    }

    @GetMapping("{id}")
    public Group getById(@PathVariable("id") long id) {
        return groupService.getById(id);
    }
}
