package com.example.EmployeePayrollApp.Repositories;

import com.example.EmployeePayrollApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by department
    List<Employee> findByDepartment(String department);

    // Find employee by name (optional in case no match is found)
    Optional<Employee> findByName(String name);
}
