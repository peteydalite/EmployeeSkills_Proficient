package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillDao {
    List<Skill> getAllSkills();
    List<Skill> getSkillsbyFieldId(UUID field_id);
    Skill getSkillById(UUID id);
    boolean updateSkill(Skill skill);
    boolean addSkill(Skill skill);
    boolean deleteSkill(Skill skill);
}
