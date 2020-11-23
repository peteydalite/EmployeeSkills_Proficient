package com.peteydalite.EmployeeSkills.controller;

import com.peteydalite.EmployeeSkills.dao.EmployeeDao;
import com.peteydalite.EmployeeSkills.dao.EmployeeToSkillsDao;
import com.peteydalite.EmployeeSkills.dao.FieldDao;
import com.peteydalite.EmployeeSkills.dao.SkillDao;
import com.peteydalite.EmployeeSkills.model.Employee;
import com.peteydalite.EmployeeSkills.model.EmployeeToSkills;
import com.peteydalite.EmployeeSkills.model.Field;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDao empDao;
    private SkillDao skillDao;
    private EmployeeToSkillsDao etsDao;

    public EmployeeController(EmployeeDao employeeDao, SkillDao skillDao, EmployeeToSkillsDao employeeToSkills){
        this.empDao = employeeDao;
        this.skillDao = skillDao;
        this.etsDao = employeeToSkills;
    }


    @RequestMapping(path= "/employees", method = RequestMethod.GET)
    public List<Employee> getAllEmp(){
        return this.empDao.getAllEmployees();
    }

    @RequestMapping(path="/employees/{employeeId}", method = RequestMethod.GET)
    public Employee getEmp(@PathVariable("employeeId")UUID employee_id){
        return this.empDao.getEmployeeById(employee_id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path ="/employees", method = RequestMethod.POST)
    public void createEmp(@Valid @RequestBody Employee employee){
        Employee check = this.empDao.getEmployeeById(employee.getId());
        if(check == null) {

            boolean created = this.empDao.addEmployee(employee);
            if (created) {
                System.out.println("Employee with id: " + employee.getId() + " created!");
            } else {
                System.out.println("Could not create employee");
            }
        }
    }


    @RequestMapping(path = "/employees/{employeeId}", method = RequestMethod.PUT)
    public void updateEmployee(@Valid @RequestBody Employee employee){
         this.empDao.updateEmployee(employee);
    }

    @RequestMapping(path="/employees/{employeeId}", method = RequestMethod.DELETE)
    public void deleteEmp(@PathVariable("employeeId") UUID employee_id){
        this.empDao.deleteEmployee(employee_id);
    }

    @RequestMapping(path="/employees/{employeeId}/skills", method = RequestMethod.GET)
    public Skill[] employeeSkills(@PathVariable("employeeId")UUID employee_id){
        Employee emp = this.empDao.getEmployeeById(employee_id);
        return emp.getSkills();
    }

    @RequestMapping(path="/employees/{employeeId}/skills", method = RequestMethod.POST)
    public Skill[] addEmployeeSkills(@PathVariable("employeeId")UUID employee_id, @RequestBody Skill skill){
        this.skillDao.addSkill(skill);
        EmployeeToSkills ets = new EmployeeToSkills(employee_id, skill.getId());
        this.etsDao.addSkillToEmployee(ets);
        Employee emp = this.empDao.getEmployeeById(employee_id);

        return emp.getSkills();

    }

    @RequestMapping(path="/employees/{employeeId}/skills/{skillId}", method = RequestMethod.GET)
    public Skill getEmployeeSkill(@PathVariable("employeeId")UUID employee_id, @PathVariable("skillId")UUID skill_id){
        Employee emp = this.empDao.getEmployeeById(employee_id);
        Skill[] skills = emp.getSkills();

        Skill skill = null;
        //check to make sure employee has skill
        for(int i =0; i< skills.length; i++){

            if(skills[i].getId().equals(skill_id)){
                skill = skills[i];
                break;
            }
        }
        return skill;
    }

    @RequestMapping(path="/employees/{employeeId}/skills/{skillId}", method = RequestMethod.PUT)
    public Employee updateEmployeeSkill(@PathVariable("employeeId")UUID employee_id, @PathVariable("skillId")UUID skill_id, @RequestBody Skill skill){
        Employee emp = this.empDao.getEmployeeById(employee_id);
        Skill[] skills = emp.getSkills();

        //Make sure the employee has the skill before updating
        for(int i =0; i< skills.length; i++){

            if(skills[i].getId().equals(skill_id)){
                this.skillDao.updateSkill(skill);
                break;
            }
        }
        return this.empDao.getEmployeeById(employee_id);
    }

    @RequestMapping(path="/employees/{employeeId}/skills/{skillId}", method = RequestMethod.DELETE)
    public boolean deleteEmployeeSkill(@PathVariable("employeeId")UUID employee_id, @PathVariable("skillId")UUID skill_id){
        EmployeeToSkills ets = new EmployeeToSkills(employee_id, skill_id);
        Employee emp = this.empDao.getEmployeeById(employee_id);
        //Delete from emp to skill table
        this.etsDao.deleteEmployeeSkill(ets);
        boolean deleted = false;

        //delete from skills table
        Skill[] skills = emp.getSkills();
        for(int i =0; i< skills.length; i++) {
            System.out.println(Arrays.toString(skills));
            System.out.println(skill_id);
            if (skills[i].getId().equals(skill_id)) {
                deleted = this.skillDao.deleteSkill(skills[i]);
                break;
            }
        }
        return deleted;
    }

}
