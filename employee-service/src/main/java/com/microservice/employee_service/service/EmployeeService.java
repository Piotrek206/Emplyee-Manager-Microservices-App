package com.microservice.employee_service.service;

import com.microservice.employee_service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeDto> findAll();

    public EmployeeDto findById(Long departmentId);

    public EmployeeDto saveEmployee(EmployeeDto departmentDto);

    public EmployeeDto findByEmail(String email);
}
