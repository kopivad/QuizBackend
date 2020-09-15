package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.SaveGroupDto;
import com.kopivad.quizzes.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody SaveGroupDto dto) {
        if (groupService.save(dto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody Group group) {
        if (groupService.update(group)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<Group>> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<List<Group>> getAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.getAllByUserId(userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<Group> getById(@PathVariable("id") long id) {
        return ResponseEntity.of(groupService.getById(id));
    }
}
