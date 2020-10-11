package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.EmployeeDao;
import com.peteydalite.EmployeeSkills.model.Employee;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService implements EmployeeDao {
    private JdbcTemplate jdbc;
    private AddressService addService;
    private SkillService skillService;
    private EmployeeToSkillsService etsService;

    public EmployeeService(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
        addService = new AddressService(this.jdbc);
        skillService = new SkillService(this.jdbc);
        etsService = new EmployeeToSkillsService(this.jdbc);
    }

    private Employee mapRowToEmployee(SqlRowSet rs){
        Employee temp = new Employee();
        temp.setEmployee_id((java.util.UUID) rs.getObject("employee_id"));
        temp.setFirstName(rs.getString("firstname"));
        temp.setLastName(rs.getString("lastname"));
        temp.setAddress(addService.getAddressById((java.util.UUID) rs.getObject("address_id")));
        temp.setContactEmail(rs.getString("contact_email"));
        temp.setCompanyEmail(rs.getString("company_email"));

        String[] bdayArr = rs.getString("birthdate").trim().split("-");
        temp.setBirthdate(LocalDate.of(Integer.parseInt(bdayArr[0]), Integer.parseInt(bdayArr[1]), Integer.parseInt(bdayArr[2])));

        String[] hireDateArr = rs.getString("hire_date").trim().split("-");
        temp.setHireDate(LocalDate.of(Integer.parseInt(hireDateArr[0]), Integer.parseInt(hireDateArr[1]), Integer.parseInt(hireDateArr[2])));
        temp.setRole(rs.getString("role"));

        //Need to call employeetoskills to get all skills for emp
        List<Skill> empSkills = this.etsService.getAllSkillsByEmployeeId(temp.getEmployee_id());
        Skill[] skillsArr = new Skill[empSkills.size()];
        skillsArr = empSkills.toArray(skillsArr);
        temp.setSkills(skillsArr);

        temp.setAssignedTo((java.util.UUID) rs.getObject("assigned_to"));

        return  temp;
    }
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> allEmployee = new ArrayList<>();
        String sqlSelect = "Select * from employee ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect);
            while(result.next()){
                Employee emp = mapRowToEmployee(result);
                allEmployee.add(emp);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return allEmployee;
    }

    @Override
    public Employee getEmployeeById(UUID employee_id) {
        Employee emp = null;
        String sqlSelect = "Select * from Employee Where employee_id = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, employee_id);
            if(result.next()){
                emp = mapRowToEmployee(result);

            }
        }catch (Exception e){
            System.out.println(e);
        }
        return emp;
    }

    @Override
    public boolean updateEmployee(Employee updateEmployee) {
        boolean employeeUpdated = false;
        boolean addressUpdated = false;
        boolean skillUpdated = false;

        //Need to update address and skills separately
        String sqlUpdate = "Update Employee set firstname = ?, lastname = ?, contact_email = ?, " +
                "company_email = ?, birthdate = ?, hire_date = ?, role = ?, business_unit = ?, assigned_to = ? " +
                "Where employee_id = ?";
        try{
            employeeUpdated = this.jdbc.update(sqlUpdate, updateEmployee.getFirstName(), updateEmployee.getLastName(),
                    updateEmployee.getContactEmail(), updateEmployee.getCompanyEmail(), updateEmployee.getBirthdate(),
                    updateEmployee.getHireDate(), updateEmployee.getRole(), updateEmployee.getBusinessUnit(), updateEmployee.getAssignedTo(),
                    updateEmployee.getEmployee_id()) == 1;

            addressUpdated = this.addService.updateAddress(updateEmployee.getAddress());

            for(Skill empSkill : updateEmployee.getSkills()){
                skillUpdated = this.skillService.updateSkill(empSkill);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return employeeUpdated == addressUpdated == skillUpdated;
    }

    @Override
    public boolean addEmployee(Employee newEmployee) {
        boolean added = false;
        String sqlInsert = "Insert into Employee Values(?,?,?,?,?,?,?,?,?,?,?) ";
        try{
            added = this.jdbc.update(sqlInsert, newEmployee.getEmployee_id(), newEmployee.getFirstName(),
                    newEmployee.getLastName(), newEmployee.getAddress().getAddress_id(), newEmployee.getContactEmail(),
                    newEmployee.getCompanyEmail(), newEmployee.getBirthdate(), newEmployee.getHireDate(),
                    newEmployee.getRole(), newEmployee.getBusinessUnit(), newEmployee.getAssignedTo()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean deleteEmployee(Employee deleteEmployee) {
        boolean deleted = false;
        String sqlDelete = "Delete from Employee where employee_id = ? ";
        try{
            deleted = this.jdbc.update(sqlDelete, deleteEmployee.getEmployee_id()) == 1;
        }catch (Exception e) {
            System.out.println(e);
        }
        return deleted;
    }
}
