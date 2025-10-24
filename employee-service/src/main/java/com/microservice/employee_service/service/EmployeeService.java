package com.microservice.employee_service.service;

import com.microservice.employee_service.dto.APIResponseDto;
import com.microservice.employee_service.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAll();

    APIResponseDto findById(Long departmentId);

    EmployeeDto findEmployeeDtoById(Long id);

    EmployeeDto findByEmail(String email);

    EmployeeDto saveEmployee(EmployeeDto departmentDto);

}
