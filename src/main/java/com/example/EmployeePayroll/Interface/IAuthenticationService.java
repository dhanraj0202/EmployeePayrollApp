package com.example.EmployeePayroll.Interface;

import com.example.EmployeePayroll.DTO.AuthUserDTO;
import com.example.EmployeePayroll.DTO.LoginDTO;
import com.example.EmployeePayroll.model.AuthUser;


public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

    String login(LoginDTO loginDTO);
    String forgotPassword(String email, String newPassword);
    String resetPassword(String email, String currentPassword, String newPassword);
}
