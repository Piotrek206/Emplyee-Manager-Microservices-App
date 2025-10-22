package com.microservice.departmentservice.service;

import com.microservice.departmentservice.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    public List<DepartmentDto> findAll();

    public DepartmentDto findById(Long departmentId);

    public DepartmentDto saveDepartment(DepartmentDto departmentDto);

    public DepartmentDto findByCode(String code);
}
