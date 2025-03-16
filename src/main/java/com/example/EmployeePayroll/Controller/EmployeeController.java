package com.example.EmployeePayroll.Controller;

import com.example.EmployeePayroll.Service.EmployeeService;
import com.example.EmployeePayroll.model.EmployeeModel;
import jakarta.validation.Valid;  // Import Valid annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeepayrollservice")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public List<EmployeeModel> getAllUsers() {
        return employeeService.getAllUsers();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeModel> getUserById(@PathVariable Long id) {
        return employeeService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeModel> createUser(@Valid @RequestBody EmployeeModel employee) {
        EmployeeModel createdUser = employeeService.createUser(employee);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeModel> updateUser(@PathVariable Long id,
                                                    @Valid @RequestBody EmployeeModel userDetails) {
        return employeeService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (employeeService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
