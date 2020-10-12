package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.SkillDao;
import com.peteydalite.EmployeeSkills.model.Field;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SkillService implements SkillDao {

    private JdbcTemplate jdbc;
    private FieldService fieldService;

    public SkillService(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
        fieldService = new FieldService(this.jdbc);
    }

    private Skill mapRowToSkill(SqlRowSet rs){
        Skill skill = new Skill();
        skill.setId((java.util.UUID) rs.getObject("skill_id"));
        skill.setExperience(rs.getInt("experience"));
        skill.setSummary(rs.getString("summary"));
        skill.setField(fieldService.getFieldById((java.util.UUID) rs.getObject("field_id")));
        return skill;
    }
    @Override
    public List<Skill> getAllSkills() {
        List<Skill> allSkills = new ArrayList<>();
        String sqlSelect = "Select * from Skills ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect);
            while(result.next()){
                Skill skill = mapRowToSkill(result);
                allSkills.add(skill);
            }
        }catch (Exception e){
            throw(e);
        }

        return allSkills;
    }

    @Override
    public List<Skill> getSkillsbyFieldId(UUID field_id) {
        List<Skill> skillsByField = new ArrayList<>();
        String sqlSelect = "Select * from Skills where field_id = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, field_id);
            while (result.next()){
                Skill skill = mapRowToSkill(result);
                skillsByField.add(skill);
            }
        }catch(Exception e){
            throw(e);
        }
        return skillsByField;
    }

    @Override
    public Skill getSkillById(UUID id) {
        Skill skill = null;
        String sqlSelect = "Select * from Skills where skill_id = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect,id);
            if(result.next()){
                skill = this.mapRowToSkill(result);
            }
        }catch(Exception e){
            throw(e);
        }
        return skill;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        boolean updated = false;
        String sqlUpdate = "Update Skills Set experience = ?, " +
                            "summary = ?, field_id = ? Where skill_id = ? ";
        try{
            updated = this.jdbc.update(sqlUpdate, skill.getExperience(), skill.getSummary(),
                    skill.getField().getId(), skill.getId()) == 1;
        }catch (Exception e){
            throw(e);
        }
        return updated;
    }

    @Override
    public boolean addSkill(Skill skill) {
        boolean addedToSkill = false;
        String sqlInsert = "Insert into Skills Values (?, ?, ?, ?) ";
        try{
            Field temp = this.fieldService.getFieldById(skill.getField().getId());
            if(temp == null){
                this.fieldService.addField(skill.getField());
            }
            addedToSkill = this.jdbc.update(sqlInsert, skill.getId(), skill.getExperience(),
                    skill.getSummary(), skill.getField().getId()) == 1;
        }catch(Exception e){
            throw(e);
        }
        return addedToSkill;
    }

    @Override
    public boolean deleteSkill(Skill skill) {
        boolean deleted = false;
        String sqlDelete = "Delete from Skills where skill_id = ? ";
        try{
            deleted = this.jdbc.update(sqlDelete, skill.getId()) == 1;
        }catch(Exception e){
            throw(e);
        }
        return deleted;
    }
}
