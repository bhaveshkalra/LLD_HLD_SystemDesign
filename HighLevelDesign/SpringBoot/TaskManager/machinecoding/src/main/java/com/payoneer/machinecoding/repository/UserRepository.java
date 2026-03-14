package com.payoneer.machinecoding.repository;

import com.payoneer.machinecoding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}