package com.practice.todo.service;

import com.practice.todo.exception.AccessDeniedException;
import com.practice.todo.exception.ResourceNotFoundException;
import com.practice.todo.model.Task;
import com.practice.todo.model.User;
import com.practice.todo.repository.TaskRepository;
import com.practice.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(Task task, Integer userId, UserDetails userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with user id = " + userId));
        if(!user.getEmail().equals(userDetails.getUsername())){
            throw new AccessDeniedException("Access Denied");
        }
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Task updatedTask, Integer userId, UserDetails userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with user id = " + userId));
        if(!user.getEmail().equals(userDetails.getUsername())){
            throw new AccessDeniedException("Access Denied");
        }
        Task existingTask = taskRepository.findById(updatedTask.getId()).orElseThrow(() -> new ResourceNotFoundException("Task not found with task id = " + updatedTask.getId()));
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setTitle(updatedTask.getTitle());
        taskRepository.save(existingTask);
        return updatedTask;
    }

    public Task getTask(Integer userId, Integer taskId, UserDetails userDetails){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with user id = " + userId));
        if(!user.getEmail().equals(userDetails.getUsername())){
            throw new AccessDeniedException("Access Denied");
        }
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id = " + taskId));
        if(user != task.getUser())
            throw new AccessDeniedException("Access Denied for task id = " + taskId);
        return task;
    }

    public List<Task> getAllTasks(Integer userId, UserDetails userDetails) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with user id = " + userId));
        if(!user.getEmail().equals(userDetails.getUsername())){
            throw new AccessDeniedException("Access Denied");
        }
        return taskRepository.findByUser(user);
    }
}
