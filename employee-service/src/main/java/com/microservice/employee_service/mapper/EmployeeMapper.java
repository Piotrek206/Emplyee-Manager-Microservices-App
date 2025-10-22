package com.microservice.employee_service.mapper;

import com.microservice.employee_service.dto.EmployeeDto;
import com.microservice.employee_service.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public EmployeeMapper() {
        this.modelMapper = new ModelMapper();
    }

    public EmployeeDto toDto(Employee department) {
        return modelMapper.map(department, EmployeeDto.class);
    }

    public Employee toEntity(EmployeeDto departmentDto) {
        return modelMapper.map(departmentDto, Employee.class);
    }
}
