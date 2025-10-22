package com.microservice.employee_service.exception;

public class EmployeeSaveException extends RuntimeException {
    public EmployeeSaveException(String message) {
        super(message);
    }
}
