package com.microservice.employee_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDto {

    private EmployeeDto employeeDto;
    private DepartmentDto departmentDto;
}
