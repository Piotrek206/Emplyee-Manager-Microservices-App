package com.microservice.employee_service.service;

import com.microservice.employee_service.dto.EmployeeDto;
import com.microservice.employee_service.entity.Employee;
import com.microservice.employee_service.exception.EmployeeNotFoundException;
import com.microservice.employee_service.exception.EmployeeSaveException;
import com.microservice.employee_service.mapper.EmployeeMapper;
import com.microservice.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> findAll() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findById(Long employeeId) {
        log.info("Fetching employee with id: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        log.info("Fetching employee with email: {}", email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with code: " + email));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        log.info("Saving employee: {}", employeeDto);
        try {
            Employee employee = employeeMapper.toEntity(employeeDto);
            Employee saved = employeeRepository.save(employee);
            return employeeMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error saving employee: {}", e.getMessage());
            throw new EmployeeSaveException("Failed to save employee");
        }
    }
}
