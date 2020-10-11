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
        newEmp.setEmployee_id(UUID.randomUUID());
        newEmp.setFirstName("TEST");
        newEmp.setLastName("TEST");
        newEmp.setCompanyEmail("TEST@company.com");
        newEmp.setBirthdate(LocalDate.of(1990, 12,12));
        newEmp.setHireDate(LocalDate.now());
        newEmp.setRole("Director");
        newEmp.setAddress(address);

        this.empService.addEmployee(newEmp);
    }
    @Test
    public void getAllEmployees() {
        List<Employee> listBefore = this.empService.getAllEmployees();
        Address address = this.addressService.getAllAddress().get(0);
        Employee newEmp = new Employee();
        newEmp.setEmployee_id(UUID.randomUUID());
        newEmp.setFirstName("TEST99");
        newEmp.setLastName("TEST99");
        newEmp.setCompanyEmail("TEST99@company.com");
        newEmp.setBirthdate(LocalDate.of(1990, 12,12));
        newEmp.setHireDate(LocalDate.now());
        newEmp.setRole("Technical Consultant");
        newEmp.setAddress(address);

        this.empService.addEmployee(newEmp);

        List<Employee> listAfter = this.empService.getAllEmployees();
        assertEquals(listBefore.size() + 1, listAfter.size());

    }

    @Test
    public void getEmployeeById() {
        Employee test = this.empService.getAllEmployees().get(0);
        Employee result = this.empService.getEmployeeById(test.getEmployee_id());

        assertEquals(test.getEmployee_id(), result.getEmployee_id());

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
        Employee result = this.empService.getEmployeeById(test.getEmployee_id());

        assertEquals("Bobby", result.getFirstName().trim());
        assertEquals("999 Test", result.getAddress().getSuite());
    }

    @Test
    public void addEmployee() {
        Address address = this.addressService.getAllAddress().get(0);
        Employee newEmp = new Employee();
        newEmp.setEmployee_id(UUID.randomUUID());
        newEmp.setFirstName("Rick");
        newEmp.setLastName("Stan");
        newEmp.setCompanyEmail("rick@company.com");
        newEmp.setBirthdate(LocalDate.of(1990, 12,12));
        newEmp.setHireDate(LocalDate.now());
        newEmp.setRole("Project Manager");
        newEmp.setAddress(address);

        boolean result = this.empService.addEmployee(newEmp);
        assertEquals(true, result);

    }

    @Test
    public void deleteEmployee() {
        Employee test = this.empService.getAllEmployees().get(0);

        this.etsService.deleteEmployeeAll(test.getEmployee_id());
        boolean deleted = this.empService.deleteEmployee(test);

        Employee result = this.empService.getEmployeeById(test.getEmployee_id());

        assertEquals(true, deleted);
        assertEquals(null, result);
    }
}