package com.example.EmployeePayroll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Name should start with a capital letter and contain only letters")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;
}
