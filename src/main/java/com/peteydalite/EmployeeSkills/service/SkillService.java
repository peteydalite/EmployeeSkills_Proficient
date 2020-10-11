package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.SkillDao;
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
        skill.setSkill_id((java.util.UUID) rs.getObject("skill_id"));
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
            System.out.println(e);
        }

        return allSkills;
    }

    @Override
    public List<Skill> getSkillsbyFieldId() {
        return null;
    }

    @Override
    public Skill getSkillById(UUID id) {
        return null;
    }

    @Override
    public boolean updateSkill(Skill skill) {
        return false;
    }

    @Override
    public boolean addSkill(Skill skill) {
        return false;
    }

    @Override
    public boolean deleteSkill(Skill skill) {
        return false;
    }
}
