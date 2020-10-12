package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.EmployeeToSkillsDao;
import com.peteydalite.EmployeeSkills.model.Employee;
import com.peteydalite.EmployeeSkills.model.EmployeeToSkills;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeToSkillsService implements EmployeeToSkillsDao {
    private JdbcTemplate jdbc;
    private SkillService skillService;
    private EmployeeService empService;

    public EmployeeToSkillsService(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
        this.skillService = new SkillService(this.jdbc);
    }

    private EmployeeToSkills mapRowToEmployeeSkills(SqlRowSet rs){
        EmployeeToSkills temp = new EmployeeToSkills();
        temp.setEmployee_id((java.util.UUID) rs.getObject("employee_id"));
        temp.setSkills_id((java.util.UUID) rs.getObject("skill_id"));

        return temp;
    }

    @Override
    public List<Skill> getAllSkillsByEmployeeId(UUID employee_id) {
        List<Skill> skillList = new ArrayList<>();

        String sqlSelect = "Select * from Employee_To_Skills where employee_id = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, employee_id);
            while(result.next()){
                EmployeeToSkills ets = mapRowToEmployeeSkills(result);
                Skill empSkill = this.skillService.getSkillById(ets.getSkills_id());

                skillList.add(empSkill);
            }
        }catch(Exception e){
            throw(e);
        }

        return skillList;
    }

    @Override
    public List<EmployeeToSkills> getAllEmployeesBySkill(UUID skill_id) {
        List<EmployeeToSkills> empList = new ArrayList<>();
        String sqlSelect = "Select * from Employee_To_Skill where skill_id = ?";

        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, skill_id);
            while(result.next()){
                EmployeeToSkills ets = mapRowToEmployeeSkills(result);
               empList.add(ets);
            }
        }catch(Exception e){
            throw(e);
        }
        return empList;
    }

    @Override
    public boolean addSkillToEmployee(EmployeeToSkills employeeSkill) {
        boolean added = false;
        String sqlInsert = "Insert into Employee_To_Skills Values (?,?) ";
        try{
            added = this.jdbc.update(sqlInsert, employeeSkill.getEmployee_id(), employeeSkill.getSkills_id()) == 1;
        }catch(Exception e){
            throw(e);
        }
        return added;
    }

    @Override
    public boolean deleteEmployeeSkill(EmployeeToSkills employeeToSkills) {
        boolean deleted = false;
        String sqlDelete = "Delete from Employee_To_Skills where employee_id = ? and skill_id = ? ";
        try{
            deleted = this.jdbc.update(sqlDelete, employeeToSkills.getEmployee_id(), employeeToSkills.getSkills_id()) == 1;
        }catch (Exception e){
            throw(e);
        }
        return deleted;
    }

    @Override
    public boolean deleteEmployeeAll(UUID employee_id) {
        boolean delete = false;
        String sqlDelete = "Delete from Employee_to_Skills where employee_id = ? ";
        try{
            delete = this.jdbc.update(sqlDelete, employee_id) == 1;
        }catch (Exception e){
            throw(e);
        }
        return  delete;
    }
}
