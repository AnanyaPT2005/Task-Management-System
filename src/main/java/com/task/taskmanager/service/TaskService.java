package com.task.taskmanager.service;

import com.task.taskmanager.exception.ResourceNotFoundException;
import com.task.taskmanager.model.Task;
import com.task.taskmanager.model.TaskResponse;
import com.task.taskmanager.model.User;
import com.task.taskmanager.model.Role;
import com.task.taskmanager.repository.TaskRepository;
import com.task.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private static final Logger logger =
        LoggerFactory.getLogger(TaskService.class);

    public Task createTask(Task task) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        logger.info("Creating task for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user);
        task.setStatus("TODO");

        return taskRepository.save(task);
    }

public List<TaskResponse> getTasks() {

    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    List<Task> tasks;

    if (user.getRole() == Role.ADMIN) {
        tasks = taskRepository.findAll();
    } else {
        tasks = taskRepository.findByUserId(user.getId());
    }

    return tasks.stream()
            .map(task -> new TaskResponse(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getUser().getName()
            ))
            .toList();
}


    public void deleteTask(Long id) {
    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

    taskRepository.delete(task);
}
}