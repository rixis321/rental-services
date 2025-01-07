package com.example.rentalservices.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{
    private String resourceName;
    private String fieldName;

    public ResourceNotFoundException(String resourceName, String fieldName) {
        super(String.format("Resource %s not found with %s", resourceName,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
