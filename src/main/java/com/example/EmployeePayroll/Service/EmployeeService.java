package com.example.EmployeePayroll.Service;

import com.example.EmployeePayroll.Interface.IemployeeService;
import com.example.EmployeePayroll.Repository.employeeRepo;
import com.example.EmployeePayroll.model.EmployeeModel;
import com.example.EmployeePayroll.Exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "employees") // Defines default cache name
public class EmployeeService implements IemployeeService {

    @Autowired
    private employeeRepo employeeRepository;

    @Override
    @Cacheable  // Cache the list of employees
    public List<EmployeeModel> getAllUsers() {
        try {
            log.info("Fetching all employees from the database.");
            return employeeRepository.findAll();
        } catch (Exception e) {
            log.error("Error fetching employee data", e);
            throw new UserException("Failed to fetch employees. Please try again later.");
        }
    }

    @Override
    @Cacheable(key = "#id")  // Cache specific employee data by ID
    public Optional<EmployeeModel> getUserById(Long id) {
        try {
            log.info("Fetching employee with ID: {}", id);
            return employeeRepository.findById(id);
        } catch (Exception e) {
            log.error("Error fetching employee with ID: {}", id, e);
            throw new UserException("Employee not found with ID: " + id);
        }
    }

    @Override
    @CachePut(key = "#user.id")  // Update cache when a new employee is created
    public EmployeeModel createUser(EmployeeModel user) {
        try {
            log.info("Creating new employee: {}", user.getName());
            return employeeRepository.save(user);
        } catch (Exception e) {
            log.error("Error creating employee: {}", user.getName(), e);
            throw new UserException("Failed to create employee. Please try again.");
        }
    }

    @Override
    @CachePut(key = "#id")  // Update cache when an employee is updated
    public Optional<EmployeeModel> updateUser(Long id, EmployeeModel userDetails) {
        try {
            log.info("Updating employee with ID: {}", id);
            return employeeRepository.findById(id).map(user -> {
                user.setName(userDetails.getName());
                user.setEmail(userDetails.getEmail());
                log.info("Updated details for employee ID: {}", id);
                return employeeRepository.save(user);
            });
        } catch (Exception e) {
            log.error("Error updating employee with ID: {}", id, e);
            throw new UserException("Failed to update employee. Please try again later.");
        }
    }

    @Override
    @CacheEvict(key = "#id")  // Remove from cache when an employee is deleted
    public boolean deleteUser(Long id) {
        try {
            if (employeeRepository.existsById(id)) {
                log.warn("Deleting employee with ID: {}", id);
                employeeRepository.deleteById(id);
                log.info("Employee with ID: {} deleted successfully.", id);
                return true;
            }
            log.error("Attempted to delete non-existing employee with ID: {}", id);
            return false;
        } catch (Exception e) {
            log.error("Error deleting employee with ID: {}", id, e);
            throw new UserException("Failed to delete employee. Please try again.");
        }
    }
}
