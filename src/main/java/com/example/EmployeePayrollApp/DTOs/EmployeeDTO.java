//package com.example.EmployeePayrollApp.DTOs;
//
//public class EmployeeDTO {
//    private String name;
//    private String department;
//    private double salary;
//
//    // Constructors
//    public EmployeeDTO() {}
//
//    public EmployeeDTO(String name, String department, double salary) {
//        this.name = name;
//        this.department = department;
//        this.salary = salary;
//    }
//
//    // Getters and Setters
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//}

//USING LOMBOK LIBRARY:
package com.example.EmployeePayrollApp.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String name;
    private String department;  // Should be String
    private double salary;      // Should be double

    public EmployeeDTO(Long id, String name, double salary, String department) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.department = department;
    }
}