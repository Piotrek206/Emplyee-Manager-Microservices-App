package com.microservice.departmentservice.controller;

import com.microservice.departmentservice.dto.DepartmentDto;
import com.microservice.departmentservice.exception.DepartmentNotFoundException;
import com.microservice.departmentservice.exception.DepartmentSaveException;
import com.microservice.departmentservice.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> findAll() {
        log.info("Received request: findAll");
        List<DepartmentDto> departments = departmentService.findAll();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> findById(@PathVariable Long id) {
        log.info("Received request: findById with id {}", id);
        try {
            DepartmentDto department = departmentService.findById(id);
            return ResponseEntity.ok(department);
        } catch (DepartmentNotFoundException ex) {
            log.error("Department not found: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("code/{code}")
    public ResponseEntity<DepartmentDto> findByCode(@PathVariable String code) {
        log.info("Received request: findByCode with code {}", code);
        try {
            DepartmentDto department = departmentService.findByCode(code);
            return ResponseEntity.ok(department);
        } catch (DepartmentNotFoundException ex) {
            log.error("Department not found by code: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        log.info("Received request: saveDepartment");
        try {
            DepartmentDto saved = departmentService.saveDepartment(departmentDto);
            return ResponseEntity.status(201).body(saved);
        } catch (DepartmentSaveException ex) {
            log.error("Error saving department: {}", ex.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
