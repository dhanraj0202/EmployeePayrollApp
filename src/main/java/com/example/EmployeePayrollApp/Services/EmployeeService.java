//package com.example.EmployeePayrollApp.Services;
//
//import com.example.EmployeePayrollApp.DTOs.EmployeeDTO;
//import com.example.EmployeePayrollApp.model.Employee;
//import com.example.EmployeePayrollApp.Repositories.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class EmployeeService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    // Get all employees
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
//
//    // Get employee by ID
//    public Optional<Employee> getEmployeeById(Long id) {
//        return employeeRepository.findById(id);
//    }
//
//    // Create a new employee
//    public Employee createEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = new Employee();
//        employee.setName(employeeDTO.getName());
//        employee.setDepartment(employeeDTO.getDepartment());
//        employee.setSalary(employeeDTO.getSalary());
//        return employeeRepository.save(employee);
//    }
//
//    // Update an employee
//    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
//        Employee employee = employeeRepository.findById(id).orElseThrow();
//        employee.setName(employeeDTO.getName());
//        employee.setDepartment(employeeDTO.getDepartment());
//        employee.setSalary(employeeDTO.getSalary());
//        return employeeRepository.save(employee);
//    }
//
//    // Delete an employee
//    public boolean deleteEmployee(Long id) {
//        if (employeeRepository.existsById(id)) {
//            employeeRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//}

/// /USING LIBRARY LOMBOK:

package com.example.EmployeePayrollApp.Services;

import com.example.EmployeePayrollApp.model.Employee;
import com.example.EmployeePayrollApp.DTOs.EmployeeDTO;
import com.example.EmployeePayrollApp.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeService implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Convert Employee to EmployeeDTO
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSalary(), employee.getDepartment());
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeDTO(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getSalary(), savedEmployee.getDepartment());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return convertToDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(employeeDTO.getDepartment());

        Employee updatedEmployee = employeeRepository.save(employee);

        return convertToDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}