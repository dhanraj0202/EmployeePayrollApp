package com.example.EmployeePayrollApp.Controllers;

import com.example.EmployeePayrollApp.model.Employee;
import com.example.EmployeePayrollApp.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET: Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // POST: Create a new employee
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // GET: Get employee by ID
    @GetMapping("/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id);
    }

    // PUT: Update employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setName(employeeDetails.getName());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        return employeeRepository.save(employee);
    }

    // DELETE: Delete employee by ID
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }
}