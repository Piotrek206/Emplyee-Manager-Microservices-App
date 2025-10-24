package com.microservice.departmentservice.service;

import com.microservice.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> findAll();

    DepartmentDto findById(Long departmentId);

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    DepartmentDto findByCode(String code);
}
