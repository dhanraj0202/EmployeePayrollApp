package com.example.EmployeePayroll.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPassword {
    @NotBlank(message = "Password cannot be empty")
    private String password;
}

