package com.practice.todo.controller;

import com.practice.todo.model.Task;
import com.practice.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer userId) {
        Task savedTask = taskService.createTask(task, userId, userDetails);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer userId) {
        Task updatedTask = taskService.updateTask(task, userId, userDetails);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @GetMapping("/get/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Integer userId, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer taskId) {
        Task task = taskService.getTask(userId, taskId, userDetails);
        System.out.println("Task asdfghjkl " + task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Task>> getALlTasks(@PathVariable Integer userId, @AuthenticationPrincipal UserDetails userDetails) {
        List<Task> tasks = taskService.getAllTasks(userId, userDetails);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
