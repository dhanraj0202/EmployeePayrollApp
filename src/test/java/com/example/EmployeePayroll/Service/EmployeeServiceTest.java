package com.example.EmployeePayroll.Service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.EmployeePayroll.Exception.UserException;
import com.example.EmployeePayroll.Repository.employeeRepo;
import com.example.EmployeePayroll.model.EmployeeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private employeeRepo employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeModel employee;

    @BeforeEach
    void setUp() {
        employee = new EmployeeModel();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
    }

    // ========================== getAllUsers() Tests ========================== //
    @Test
    void testGetAllUsers_Success() {
        List<EmployeeModel> employees = new ArrayList<>();
        employees.add(employee);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeModel> result = employeeService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("john.doe@example.com", result.get(0).getEmail());
    }

    @Test
    void testGetAllUsers_Failure() {
        when(employeeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        UserException exception = assertThrows(UserException.class, () -> employeeService.getAllUsers());
        assertEquals("Failed to fetch employees. Please try again later.", exception.getMessage());
    }

    // ========================== getUserById() Tests ========================== //
    @Test
    void testGetUserById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<EmployeeModel> result = employeeService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }


    // ========================== createUser() Tests ========================== //
    @Test
    void testCreateUser_Success() {
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employee);

        EmployeeModel createdEmployee = employeeService.createUser(employee);

        assertEquals("John Doe", createdEmployee.getName());
        assertEquals("john.doe@example.com", createdEmployee.getEmail());
    }

    @Test
    void testCreateUser_Failure() {
        when(employeeRepository.save(any(EmployeeModel.class))).thenThrow(new RuntimeException("Database error"));

        UserException exception = assertThrows(UserException.class, () -> employeeService.createUser(employee));
        assertEquals("Failed to create employee. Please try again.", exception.getMessage());
    }

    // ========================== updateUser() Tests ========================== //
    @Test
    void testUpdateUser_Success() {
        EmployeeModel updatedDetails = new EmployeeModel();
        updatedDetails.setName("Jane Doe");
        updatedDetails.setEmail("jane.doe@example.com");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(updatedDetails);

        Optional<EmployeeModel> result = employeeService.updateUser(1L, updatedDetails);

        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().getName());
        assertEquals("jane.doe@example.com", result.get().getEmail());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EmployeeModel> result = employeeService.updateUser(1L, employee);

        assertFalse(result.isPresent());
    }

    // ========================== deleteUser() Tests ========================== //
    @Test
    void testDeleteUser_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        boolean result = employeeService.deleteUser(1L);

        assertTrue(result);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        boolean result = employeeService.deleteUser(1L);

        assertFalse(result);
        verify(employeeRepository, never()).deleteById(1L);
    }

    @Test
    void testDeleteUser_Failure() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteById(1L);

        UserException exception = assertThrows(UserException.class, () -> employeeService.deleteUser(1L));
        assertEquals("Failed to delete employee. Please try again.", exception.getMessage());
    }
}
