package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.model.Address;
import com.peteydalite.EmployeeSkills.model.Employee;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EmployeeServiceTest extends DaoIntergrationTest{
    private DataSource dataSource;
    private EmployeeService empService;
    private JdbcTemplate jdbc;
    private AddressService addressService;
    private EmployeeToSkillsService etsService;
    @Before
    public void setup(){
        dataSource = this.getDataSource();
        jdbc = new JdbcTemplate(dataSource);
        empService = new EmployeeService(jdbc);
        addressService = new AddressService(jdbc);
        etsService = new EmployeeToSkillsService(jdbc);

        Address address = this.addressService.getAllAddress().get(0);
        Employee newEmp = new Employee();
        newEmp.setId(UUID.randomUUID());
        newEmp.setFirstName("TEST");
        newEmp.setLastName("TEST");
        newEmp.setCompanyEmail("TEST@company.com");
        newEmp.setBirthdate("1990-12-12");
        newEmp.setHireDate(LocalDate.now().toString());
        newEmp.setRole("Director");
        newEmp.setAddress(address);

        this.empService.addEmployee(newEmp);
    }
    @Test
    public void getAllEmployees() {
        List<Employee> listBefore = this.empService.getAllEmployees();
        Address address = this.addressService.getAllAddress().get(0);
        Employee newEmp = new Employee();
        newEmp.setId(UUID.randomUUID());
        newEmp.setFirstName("TEST99");
        newEmp.setLastName("TEST99");
        newEmp.setCompanyEmail("TEST99@company.com");
        newEmp.setBirthdate("1990-12-12");
        newEmp.setHireDate(LocalDate.now().toString());
        newEmp.setRole("Technical Consultant");
        newEmp.setAddress(address);

        this.empService.addEmployee(newEmp);

        List<Employee> listAfter = this.empService.getAllEmployees();
        assertEquals(listBefore.size() + 1, listAfter.size());

    }

    @Test
    public void getEmployeeById() {
        Employee test = this.empService.getAllEmployees().get(0);
        Employee result = this.empService.getEmployeeById(test.getId());

        assertEquals(test.getId(), result.getId());

    }

    @Test
    public void updateEmployee() {
        Employee test = this.empService.getAllEmployees().get(0);
        test.setFirstName("Bobby");
        Address testAddress = test.getAddress();
        testAddress.setSuite("999 Test");
        test.setAddress(testAddress);

        boolean updated = this.empService.updateEmployee(test);
        assertEquals(true, updated);
        Employee result = this.empService.getEmployeeById(test.getId());

        assertEquals("Bobby", result.getFirstName().trim());
        assertEquals("999 Test", result.getAddress().getSuite());
    }

    @Test
    public void addEmployee() {
        Address address = this.addressService.getAllAddress().get(0);
        Employee newEmp = new Employee();
        newEmp.setId(UUID.randomUUID());
        newEmp.setFirstName("Rick");
        newEmp.setLastName("Stan");
        newEmp.setCompanyEmail("rick@company.com");
        newEmp.setBirthdate("1990-12-12");
        newEmp.setHireDate(LocalDate.now().toString());
        newEmp.setRole("Project Manager");
        newEmp.setAddress(address);

        boolean result = this.empService.addEmployee(newEmp);
        assertEquals(true, result);

    }

    @Test
    public void deleteEmployee() {
        Employee test = this.empService.getAllEmployees().get(0);

        this.etsService.deleteEmployeeAll(test.getId());
        boolean deleted = this.empService.deleteEmployee(test.getId());

        Employee result = this.empService.getEmployeeById(test.getId());

        assertEquals(true, deleted);
        assertEquals(null, result);
    }
}