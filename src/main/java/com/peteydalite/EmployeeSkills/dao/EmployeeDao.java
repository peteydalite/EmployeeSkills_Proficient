package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long employee_id);
    boolean updateEmployee(Employee updateEmployee);
    boolean addEmployee(Employee newEmployee);
    boolean deleteEmployee(Employee deleteEmployee);
}
