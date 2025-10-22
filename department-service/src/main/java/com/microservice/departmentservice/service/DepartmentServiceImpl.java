package com.microservice.departmentservice.service;

import com.microservice.departmentservice.dto.DepartmentDto;
import com.microservice.departmentservice.entity.Department;
import com.microservice.departmentservice.exception.DepartmentNotFoundException;
import com.microservice.departmentservice.exception.DepartmentSaveException;
import com.microservice.departmentservice.mapper.DepartmentMapper;
import com.microservice.departmentservice.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDto> findAll() {
        log.info("Fetching all departments");
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto findById(Long departmentId) {
        log.info("Fetching department with id: {}", departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + departmentId));
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDto findByCode(String code) {
        log.info("Fetching department with code: {}", code);
        Department department = departmentRepository.findByDepartmentCode(code)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with code: " + code));
        return departmentMapper.toDto(department);
    }

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        log.info("Saving department: {}", departmentDto);
        try {
            Department department = departmentMapper.toEntity(departmentDto);
            Department saved = departmentRepository.save(department);
            return departmentMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Error saving department: {}", e.getMessage());
            throw new DepartmentSaveException("Failed to save department");
        }
    }
}
