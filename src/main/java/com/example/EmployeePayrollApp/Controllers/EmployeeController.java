package com.example.EmployeePayrollApp.Controllers;//package com.example.EmployeePayrollApp.Controllers;
//
//import com.example.EmployeePayrollApp.DTOs.EmployeeDTO;
//import com.example.EmployeePayrollApp.model.Employee;
//import com.example.EmployeePayrollApp.Services.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/employees")
//public class EmployeeController {
//
//    @Autowired
//    private EmployeeService employeeService;
//
//    // Get all employees
//    @GetMapping
//    public List<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }
//
//    // Get employee by ID
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
//        Optional<Employee> employee = employeeService.getEmployeeById(id);
//        return employee.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Create a new employee using DTO
//    @PostMapping("/create")
//    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO) {
//        return employeeService.createEmployee(employeeDTO);
//    }
//
//    // Update an employee using DTO
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
//        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
//        return ResponseEntity.ok(updatedEmployee);
//    }
//
//    // Delete employee by ID
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
//        boolean isDeleted = employeeService.deleteEmployee(id);
//        if (isDeleted) {
//            return ResponseEntity.ok("Employee deleted successfully.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}

import com.example.EmployeePayrollApp.DTOs.EmployeeDTO;
import com.example.EmployeePayrollApp.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeepayrollservice")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/employee")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping("/update/{id}")  // Here we specify {id} in the path
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);  // Pass the id and DTO to service layer
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}