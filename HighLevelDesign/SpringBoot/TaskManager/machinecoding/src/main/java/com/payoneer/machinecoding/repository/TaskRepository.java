package com.payoneer.machinecoding.repository;

import com.payoneer.machinecoding.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}