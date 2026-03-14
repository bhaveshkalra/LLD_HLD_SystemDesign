package com.payoneer.machinecoding.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
	private Long id;
	private String name;
	private String email;
	private LocalDate dateOfJoining;
	private String departmentId;
}