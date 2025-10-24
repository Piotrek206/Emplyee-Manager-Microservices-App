package com.microservice.employee_service.service;

import com.microservice.employee_service.dto.APIResponseDto;
import com.microservice.employee_service.dto.DepartmentDto;
import com.microservice.employee_service.dto.EmployeeDto;
import com.microservice.employee_service.entity.Employee;
import com.microservice.employee_service.exception.DepartmentNotFoundException;
import com.microservice.employee_service.exception.EmployeeNotFoundException;
import com.microservice.employee_service.exception.EmployeeSaveException;
import com.microservice.employee_service.mapper.EmployeeMapper;
import com.microservice.employee_service.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
//    private final RestTemplate restTemplate;
//    private final WebClient webClient;
    private final APIClient apiClient;

    @Override
    public List<EmployeeDto> findAll() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public APIResponseDto findById(Long employeeId) {
        log.info("Fetching employee with id: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        if (employee.getDepartmentCode() == null) {
            log.warn("No department code for employee id: {}", employeeId);
            throw new DepartmentNotFoundException("No department code");
        }

        DepartmentDto departmentDto;
        try {
            departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

//            departmentDto = webClient.get()
//                    .uri("http://localhost:8080/api/departments/code/" + employee.getDepartmentCode())
//                    .retrieve()
//                    .bodyToMono(DepartmentDto.class)
//                    .block();

//            alternative using RestTemplate:
//            departmentDto = restTemplate.getForObject(
//                    "http://localhost:8080/api/departments/code/" + employee.getDepartmentCode(),
//                    DepartmentDto.class);

            if (departmentDto == null) {
                log.warn("Department not found for code: {}", employee.getDepartmentCode());
                throw new DepartmentNotFoundException("Department not found");
            }
        } catch (Exception e) {
            log.error("Error fetching department: {}", e.getMessage());
            throw new DepartmentNotFoundException("Department not found");
        }

        return APIResponseDto.builder()
                .employeeDto(employeeMapper.toDto(employee))
                .departmentDto(departmentDto)
                .build();
    }

    @Override
    public EmployeeDto findEmployeeDtoById(Long id) {
        log.info("Fetching employeeDto for id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
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
