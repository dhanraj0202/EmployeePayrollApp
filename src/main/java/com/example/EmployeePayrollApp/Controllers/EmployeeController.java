package com.example.EmployeePayrollApp.Controllers;

import com.example.EmployeePayrollApp.DTOs.EmployeeDTO;
import com.example.EmployeePayrollApp.model.Employee;
import com.example.EmployeePayrollApp.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees") // Keeping it RESTful
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET: Get all employees (returns List of EmployeeDTO)
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getDepartment(), emp.getSalary()))
                .collect(Collectors.toList());
    }

    // GET: Get employee by ID (returns EmployeeDTO)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(emp -> ResponseEntity.ok(new EmployeeDTO(emp.getName(), emp.getDepartment(), emp.getSalary())))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create a new employee using DTO
    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(new EmployeeDTO(savedEmployee.getName(), savedEmployee.getDepartment(), savedEmployee.getSalary()));
    }

    // PUT: Update an employee using DTO
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDTO.getName());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setSalary(employeeDTO.getSalary());
            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(new EmployeeDTO(updatedEmployee.getName(), updatedEmployee.getDepartment(), updatedEmployee.getSalary()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Delete employee by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}