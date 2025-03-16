package com.example.EmployeePayroll.Interface;
import com.example.EmployeePayroll.model.EmployeeModel;

import java.util.List;
import java.util.Optional;

public interface IemployeeService {
    List<EmployeeModel> getAllUsers();
    Optional<EmployeeModel> getUserById(Long id);
    EmployeeModel createUser(EmployeeModel user);
    Optional<EmployeeModel> updateUser(Long id, EmployeeModel userDetails);
    boolean deleteUser(Long id);
}
