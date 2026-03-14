package com.payoneer.machinecoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* Spring Boot backend
H2 database
React frontend
Axios API calls
* React UI
 ↓
Axios API
 ↓
Spring Boot REST
 ↓
H2 Database
*
It supports: minimal Task Manager app
Create task
List tasks
Delete task
*
* */

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
