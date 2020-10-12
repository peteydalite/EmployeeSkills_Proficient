package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(UUID employee_id);
    boolean updateEmployee(Employee updateEmployee);
    boolean addEmployee(Employee newEmployee);
    boolean deleteEmployee(UUID deleteEmployee);
}
