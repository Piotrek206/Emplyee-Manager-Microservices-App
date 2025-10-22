package com.microservice.departmentservice.exception;

public class DepartmentSaveException extends RuntimeException {
    public DepartmentSaveException(String message) {
        super(message);
    }
}
