package com.example.EmployeePayroll.Service;

import com.example.EmployeePayroll.DTO.AuthUserDTO;
import com.example.EmployeePayroll.DTO.LoginDTO;
import com.example.EmployeePayroll.Exception.UserException;
import com.example.EmployeePayroll.Interface.IAuthenticationService;
import com.example.EmployeePayroll.Repository.AuthUserRepository;
import com.example.EmployeePayroll.Util.EmailSenderService;
import com.example.EmployeePayroll.Util.jwttoken;
import com.example.EmployeePayroll.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "authUsers") // Default cache name
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    jwttoken tokenUtil;

    @Autowired
    EmailSenderService emailSenderService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @CachePut(key = "#userDTO.email")  // Cache new user upon registration
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        try {
            log.info("Starting registration process for user: {}", userDTO.getEmail());
            AuthUser user = new AuthUser(userDTO);

            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encryptedPassword);

            String token = tokenUtil.createToken(user.getUserId());
            authUserRepository.save(user);

            emailSenderService.sendEmail(user.getEmail(), "Registered in Greeting App", "Hi "
                    + user.getFirstName() + ",\nYou have been successfully registered!\n\nUser details:\n\n User Id:  "
                    + user.getUserId() + "\nFirst Name:  "
                    + user.getFirstName() + "\nLast Name:  "
                    + user.getLastName() + "\nEmail:  "
                    + user.getEmail() + "\nToken:  " + token);

            log.info("User registered successfully: {}", user.getEmail());
            return user;
        } catch (Exception e) {
            log.error("Error during registration process for user: {}", userDTO.getEmail(), e);
            throw new UserException("Failed to register user. Please try again later.");
        }
    }

    @Override
    @Cacheable(key = "#loginDTO.email")  // Cache login details
    public String login(LoginDTO loginDTO) {
        try {
            log.info("Attempting login for user: {}", loginDTO.getEmail());
            Optional<AuthUser> user = Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));

            if (user.isPresent()) {
                if (passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                    emailSenderService.sendEmail(user.get().getEmail(), "Logged in Successfully!", "Hi "
                            + user.get().getFirstName() + ",\n\nYou have successfully logged in to the Greeting App!");
                    log.info("Login successful for user: {}", loginDTO.getEmail());
                    return "Congratulations!! You have logged in successfully!";
                } else {
                    log.warn("Incorrect password for user: {}", loginDTO.getEmail());
                    throw new UserException("Sorry! Email or Password is incorrect!");
                }
            } else {
                log.warn("User not found for email: {}", loginDTO.getEmail());
                throw new UserException("Sorry! Email or Password is incorrect!");
            }
        } catch (Exception e) {
            log.error("Error during login process for user: {}", loginDTO.getEmail(), e);
            throw new UserException("Failed to log in. Please try again later.");
        }
    }

    @Override
    @CacheEvict(key = "#email")  // Clear cache after password update
    public String forgotPassword(String email, String newPassword) {
        try {
            log.info("Processing forgot password request for user: {}", email);
            AuthUser user = authUserRepository.findByEmail(email);
            if (user == null) {
                log.error("User not found for email: {}", email);
                throw new UserException("Sorry! We cannot find the user email: " + email);
            }
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            authUserRepository.save(user);

            emailSenderService.sendEmail(user.getEmail(),
                    "Password Reset Successful",
                    "Hi " + user.getFirstName() + ",\n\nYour password has been successfully changed!");
            log.info("Password changed successfully for user: {}", email);

            return "Password has been changed successfully!";
        } catch (Exception e) {
            log.error("Error while processing forgot password for email: {}", email, e);
            throw new UserException("Failed to reset password. Please try again later.");
        }
    }

    @Override
    @CacheEvict(key = "#email")  // Clear cache when password is reset
    public String resetPassword(String email, String currentPassword, String newPassword) {
        try {
            log.info("Processing reset password request for user: {}", email);
            AuthUser user = authUserRepository.findByEmail(email);
            if (user == null) {
                log.error("User not found for email: {}", email);
                throw new UserException("User not found with email: " + email);
            }

            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                log.warn("Incorrect current password for user: {}", email);
                throw new UserException("Current password is incorrect!");
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            authUserRepository.save(user);

            emailSenderService.sendEmail(user.getEmail(),
                    "Password Reset Successful",
                    "Hi " + user.getFirstName() + ",\n\nYour password has been successfully updated!");

            log.info("Password reset successfully for user: {}", email);
            return "Password reset successfully!";
        } catch (Exception e) {
            log.error("Error during password reset for user: {}", email, e);
            throw new UserException("Failed to reset password. Please try again later.");
        }
    }
}
