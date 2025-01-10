package com.example.rentalservices.mapper;

import com.example.rentalservices.model.Employee;
import com.example.rentalservices.payload.NewEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee mapToEmployee(NewEmployee newEmployee);
}
