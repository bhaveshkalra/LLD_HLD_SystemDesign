package com.payoneer.machinecoding.service;

import com.payoneer.machinecoding.entity.Task;
import com.payoneer.machinecoding.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo){
        this.repo = repo;
    }

    public List<Task> getAllTasks(){
        return repo.findAll();
    }

    public Task createTask(Task task){
        return repo.save(task);
    }

    public void deleteTask(Long id){
        repo.deleteById(id);
    }
}