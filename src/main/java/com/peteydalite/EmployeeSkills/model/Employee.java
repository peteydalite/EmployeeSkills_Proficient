package com.peteydalite.EmployeeSkills.model;

import java.time.LocalDate;
import java.util.UUID;

public class Employee {
    private UUID employee_id;
    private String firstName;
    private String lastName;
    private Address address;
    private String contactEmail;
    private String companyEmail;
    private LocalDate birthdate;
    private LocalDate hireDate;
    private String role;
    private String businessUnit;
    private Skill[] skills;
    private UUID assignedTo;

    public Employee(){};

    public Employee(UUID employee_id, String firstName, String lastName, Address address, String contactEmail, String companyEmail, LocalDate birthdate, LocalDate hireDate, String role, String businessUnit, Skill[] skills, UUID assignedTo) {
        this.employee_id = employee_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactEmail = contactEmail;
        this.companyEmail = companyEmail;
        this.birthdate = birthdate;
        this.hireDate = hireDate;
        this.role = role;
        this.businessUnit = businessUnit;
        this.skills = skills;
        this.assignedTo = assignedTo;
    }

    public UUID getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(UUID employee_id) {
        this.employee_id = employee_id;
    }

    public Address getAddress() {

        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public void setSkills(Skill[] skills) {
        this.skills = skills;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public UUID getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UUID assignedTo) {
        this.assignedTo = assignedTo;
    }
}
