package com.microservice.employee_service.service;

import com.microservice.employee_service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080/api/departments", value = "DEPARTMENT-SERVICE")
public interface APIClient {

    @GetMapping("code/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
