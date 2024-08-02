package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("{id}")
    public TaskDto getById(final @PathVariable("id") Long id) {
        final Task task = this.taskService.getById(id);
        return this.taskMapper.toDto(task);
    }

    @PutMapping
    public TaskDto update(final @Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        final Task task = this.taskMapper.toEntity(dto);
        final Task updated = this.taskService.update(task);
        return this.taskMapper.toDto(updated);
    }
    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable("id") Long id) {
        this.taskService.delete(id);
    }
}
