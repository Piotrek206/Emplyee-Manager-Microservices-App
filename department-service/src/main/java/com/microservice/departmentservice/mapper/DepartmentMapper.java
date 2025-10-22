package com.microservice.departmentservice.mapper;

import com.microservice.departmentservice.dto.DepartmentDto;
import com.microservice.departmentservice.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    private final ModelMapper modelMapper;

    public DepartmentMapper() {
        this.modelMapper = new ModelMapper();
    }

    public DepartmentDto toDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }

    public Department toEntity(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
}

