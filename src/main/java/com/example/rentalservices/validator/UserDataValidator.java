package com.example.rentalservices.validator;

import com.example.rentalservices.exception.RentalServiceApiException;
import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.payload.customer.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserDataValidator {
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    public UserDataValidator(EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    // function that validate customer data WITHOUT PESEL NUMBER
    public boolean validateCustomerData(NewCustomer newCustomer) {
        return validateString(newCustomer.getFirstName()) && validateString(newCustomer.getLastName())
                && validateCustomerEmail(newCustomer.getEmail()) && validatePhoneNumber(newCustomer.getPhone(), "customer")
                && validatePassword(newCustomer.getPassword());
    }
    // function that validate employee data
    public boolean validateEmployeeData(NewEmployee newEmployee) {
        return validateString(newEmployee.getFirstName()) && validateString(newEmployee.getLastName())
                && validateCustomerEmail(newEmployee.getEmail()) && validatePhoneNumber(newEmployee.getPhone(), "employee")
                && validatePassword(newEmployee.getPassword()) && isAdminOrEditor(newEmployee.getRoleName());
    }

    private boolean validateEmployeeEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if(email != null){
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()){
                if(employeeRepository.existsByEmail(email)){
                    throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"account with that email already exists");
                }else
                    return true;
            }else{
                throw new ValidationException("Invalid email");
            }
        }else{
            throw new ValidationException("Email cannot be null");
        }

    }

    private boolean validateCustomerEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if(email != null){
            Matcher matcher = pattern.matcher(email);
            if(matcher.matches()){
                if(customerRepository.existsByEmail(email)){
                    throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"account with that email already exists");
                }else
                    return true;
            }else{
                throw new ValidationException("Invalid email");
            }
        }else{
            throw new ValidationException("Email cannot be null");
        }
    }
   /* min 8 characters
    One upper letter
    min 3 numbers */
    private boolean validatePassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d.*\\d.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        if (password != null) {
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                return true;
            } else {
                throw new ValidationException("Password must contain at least 8 characters( One upper letter and minimum 3 numberes).");
            }
        } else {
            throw new ValidationException("Password cannot be null");
        }
    }

    private boolean validatePhoneNumber(String phoneNumber, String userType){
        String regex = "^\\s*[0-9]{3}\\s*[0-9]{3}\\s*[0-9]{3}\\s*$";
        Pattern pattern = Pattern.compile(regex);
        if(phoneNumber != null){
            Matcher matcher = pattern.matcher(phoneNumber);

            if(matcher.matches()){
                if(userType.equals("customer")){
                    if(customerRepository.existsByPhone(phoneNumber)){
                        throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"account with that phone number already exists");
                    }else
                        return true;
                }else {
                    if(employeeRepository.existsByPhone(phoneNumber)){
                        throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"account with that phone number already exists");
                    }else
                        return true;
                }

            }else{
                throw new ValidationException("Invalid phone number");
            }
        }else{
            throw new ValidationException("Phone number cannot be null");
        }

    }

    /*Function for validating name and lastname*/
    private boolean validateString(String name){
        if((name == null || name.isEmpty())|| !name.matches("^\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*\\s*$")){
            throw new ValidationException("Invalid name");
        }
        return  true;
    }

    private boolean isAdminOrEditor(String role) {
        if (role == null) {
            throw new ValidationException("Role cannot be null");
        }
        return role.equals("ADMIN") || role.equals("EDITOR");
    }

}
