package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Employee;
import com.peteydalite.EmployeeSkills.model.EmployeeToSkills;
import com.peteydalite.EmployeeSkills.model.Skill;

import java.util.List;
import java.util.UUID;

public interface EmployeeToSkillsDao {
    List<Skill> getAllSkillsByEmployeeId(UUID employee_id);
    List<EmployeeToSkills> getAllEmployeesBySkill(UUID skill_id);
    boolean addSkillToEmployee(EmployeeToSkills employeeSkill);
    boolean deleteEmployeeSkill(EmployeeToSkills employeeToSkills);
    boolean deleteEmployeeAll(UUID employee_id);
}
