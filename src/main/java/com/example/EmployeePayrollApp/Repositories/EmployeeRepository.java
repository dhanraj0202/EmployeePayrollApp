package com.example.EmployeePayrollApp.Repositories;

import com.example.EmployeePayrollApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}