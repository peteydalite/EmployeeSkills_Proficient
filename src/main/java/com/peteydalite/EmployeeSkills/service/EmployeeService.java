package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.EmployeeDao;
import com.peteydalite.EmployeeSkills.model.Employee;
import com.peteydalite.EmployeeSkills.model.EmployeeToSkills;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
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
        temp.setId((java.util.UUID) rs.getObject("employee_id"));
        temp.setFirstName(rs.getString("firstname"));
        temp.setLastName(rs.getString("lastname"));
        temp.setAddress(addService.getAddressById((java.util.UUID) rs.getObject("address_id")));
        temp.setContactEmail(rs.getString("contact_email"));
        temp.setCompanyEmail(rs.getString("company_email"));
        temp.setBirthdate(rs.getString("birthdate").trim());
        temp.setHireDate(rs.getString("hire_date").trim());
        temp.setRole(rs.getString("role"));

        //Need to call employeetoskills to get all skills for emp
        List<Skill> empSkills = this.etsService.getAllSkillsByEmployeeId(temp.getId());
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
            throw(e);
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
            throw(e);
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
            String[] bdayArr = updateEmployee.getBirthdate().split("-");
            String[] hireArr = updateEmployee.getHireDate().split("-");
            LocalDate bday = LocalDate.of(Integer.parseInt(bdayArr[0]), Integer.parseInt(bdayArr[1]), Integer.parseInt(bdayArr[2]));
            LocalDate hireDate = LocalDate.of(Integer.parseInt(hireArr[0]), Integer.parseInt(hireArr[1]), Integer.parseInt(hireArr[2]));

            employeeUpdated = this.jdbc.update(sqlUpdate, updateEmployee.getFirstName(), updateEmployee.getLastName(),
                    updateEmployee.getContactEmail(), updateEmployee.getCompanyEmail(), bday,
                    hireDate, updateEmployee.getRole(), updateEmployee.getBusinessUnit(), updateEmployee.getAssignedTo(),
                    updateEmployee.getId()) == 1;

            addressUpdated = this.addService.updateAddress(updateEmployee.getAddress());

            for(Skill empSkill : updateEmployee.getSkills()){
                skillUpdated = this.skillService.updateSkill(empSkill);
            }
        }catch (Exception e){
            throw(e);
        }
        return employeeUpdated == addressUpdated == skillUpdated;
    }

    @Override
    public boolean addEmployee(Employee newEmployee) {
        boolean added = false;

        String sqlInsert = "Insert into Employee Values(?,?,?,?,?,?,?,?,?,?,?) ";
        try{
            System.out.println(newEmployee.toString());
            this.addService.addAddress(newEmployee.getAddress());

            String[] bdayArr = newEmployee.getBirthdate().split("-");
            String[] hireArr = newEmployee.getHireDate().split("-");
            LocalDate bday = LocalDate.of(Integer.parseInt(bdayArr[0]), Integer.parseInt(bdayArr[1]), Integer.parseInt(bdayArr[2]));
            LocalDate hireDate = LocalDate.of(Integer.parseInt(hireArr[0]), Integer.parseInt(hireArr[1]), Integer.parseInt(hireArr[2]));

            added = this.jdbc.update(sqlInsert, newEmployee.getId(), newEmployee.getFirstName(),
                    newEmployee.getLastName(), newEmployee.getAddress().getId(), newEmployee.getContactEmail(),
                    newEmployee.getCompanyEmail(), bday, hireDate,
                    newEmployee.getRole(), newEmployee.getBusinessUnit(), newEmployee.getAssignedTo()) == 1;

            if(newEmployee.getSkills().length != 0){
                for(Skill empSkill : newEmployee.getSkills()){
                    this.skillService.addSkill(empSkill);
                    EmployeeToSkills es = new EmployeeToSkills(newEmployee.getId(), empSkill.getId());
                    this.etsService.addSkillToEmployee(es);
                }
            }
        }catch (Exception e){
            throw(e);
        }
        return added;
    }

    @Override
    public boolean deleteEmployee(UUID deleteEmployee) {
        boolean deleted = false;
        String sqlDelete = "Delete from Employee where employee_id = ? ";
        try{

            Employee tempEmp = this.getEmployeeById(deleteEmployee);

            //Delete the data from Employee_To_skill
            this.etsService.deleteEmployeeAll(deleteEmployee);


            //Delete associated skills on skills table
            Skill[] dletSkills = tempEmp.getSkills();
            System.out.println(Arrays.toString(dletSkills));
            if(dletSkills.length != 0){
                for(int i = 0; i < dletSkills.length; i++){
                    this.skillService.deleteSkill(dletSkills[i]);
                }
            }
            //delete employee
            deleted = this.jdbc.update(sqlDelete, deleteEmployee) == 1;

            //Delete employee address
            this.addService.deleteAddress(tempEmp.getAddress());

        }catch (Exception e) {
            throw(e);
        }
        return deleted;
    }
}
