package com.example.EmployeePayroll.Repository;

import com.example.EmployeePayroll.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface employeeRepo extends JpaRepository<EmployeeModel, Long> {
}

