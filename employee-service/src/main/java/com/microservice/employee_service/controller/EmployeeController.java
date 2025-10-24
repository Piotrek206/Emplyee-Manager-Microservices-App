package com.microservice.employee_service.controller;

import com.microservice.employee_service.dto.APIResponseDto;
import com.microservice.employee_service.dto.EmployeeDto;
import com.microservice.employee_service.exception.DepartmentNotFoundException;
import com.microservice.employee_service.exception.EmployeeNotFoundException;
import com.microservice.employee_service.exception.EmployeeSaveException;
import com.microservice.employee_service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        log.info("Received request: findAll");
        List<EmployeeDto> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponseDto> findById(@PathVariable Long id) {
        log.info("Received request: findById with id {}", id);
        try {
            APIResponseDto apiResponseDto = employeeService.findById(id);
            return ResponseEntity.ok(apiResponseDto);
        } catch (EmployeeNotFoundException ex) {
            log.error("Employee not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DepartmentNotFoundException ex) {
            log.warn("Department not found for employee id: {}", id);
            APIResponseDto response = APIResponseDto.builder()
                    .employeeDto(employeeService.findEmployeeDtoById(id))
                    .departmentDto(null)
                    .build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("email")
    public ResponseEntity<EmployeeDto> findByEmail(@RequestParam String email) {
        log.info("Received request: find employee by email with email {}", email);
        try {
            EmployeeDto employee = employeeService.findByEmail(email);
            return ResponseEntity.ok(employee);
        } catch (EmployeeNotFoundException ex) {
            log.error("Employee not found by email: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("Received request: saveEmployee");
        try {
            EmployeeDto saved = employeeService.saveEmployee(employeeDto);
            return ResponseEntity.status(201).body(saved);
        } catch (EmployeeSaveException ex) {
            log.error("Error saving employee: {}", ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
