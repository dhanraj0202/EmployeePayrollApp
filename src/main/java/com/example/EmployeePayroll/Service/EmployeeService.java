package com.example.EmployeePayroll.Service;

import com.example.EmployeePayroll.Interface.IemployeeService;
import com.example.EmployeePayroll.Repository.employeeRepo;
import com.example.EmployeePayroll.model.EmployeeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeService implements IemployeeService {

    @Autowired
    private employeeRepo employeeRepository;

    @Override
    public List<EmployeeModel> getAllUsers() {
        log.info("Fetching all employees from the database.");
        return employeeRepository.findAll();
    }

    @Override
    public Optional<EmployeeModel> getUserById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public EmployeeModel createUser(EmployeeModel user) {
        log.info("Creating new employee: {}", user.getName());
        return employeeRepository.save(user);
    }

    @Override
    public Optional<EmployeeModel> updateUser(Long id, EmployeeModel userDetails) {
        log.info("Updating employee with ID: {}", id);
        return employeeRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            log.info("Updated details for employee ID: {}", id);
            return employeeRepository.save(user);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (employeeRepository.existsById(id)) {
            log.warn("Deleting employee with ID: {}", id);
            employeeRepository.deleteById(id);
            log.info("Employee with ID: {} deleted successfully.", id);
            return true;
        }
        log.error("Attempted to delete non-existing employee with ID: {}", id);
        return false;
    }
}
