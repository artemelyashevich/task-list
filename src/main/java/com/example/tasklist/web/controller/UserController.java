package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @GetMapping("{id}/tasks")
    public List<TaskDto> getAllTasks(final @PathVariable("id") Long id) {
        final List<Task> tasks = this.taskService.getAllByUserId(id);
        return this.taskMapper.toDto(tasks);
    }

    @GetMapping("{id}")
    public UserDto getById(final @PathVariable("id") Long id) {
        final User user = this.userService.getById(id);
        return this.userMapper.toDto(user);
    }

    @DeleteMapping("{id}")
    public void deleteById(final @PathVariable("id") Long id) {
        this.userService.delete(id);
    }

    @PostMapping("{id}/tasks")
    public TaskDto createTask(
       final @PathVariable("id") Long id,
       final @Validated(OnCreate.class) @RequestBody TaskDto dto
    ) {
        final Task task = this.taskMapper.toEntity(dto);
        final Task createdTask = this.taskService.create(task);
        return this.taskMapper.toDto(createdTask);
    }

    @PutMapping
    public UserDto update(final @Validated(OnUpdate.class) @RequestBody UserDto dto) {
        final User user = this.userMapper.toEntity(dto);
        final User updatedUser = this.userService.update(user);
        return this.userMapper.toDto(updatedUser);
    }
}
