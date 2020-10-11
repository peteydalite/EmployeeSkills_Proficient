package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.model.Field;
import com.peteydalite.EmployeeSkills.model.Skill;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

public class SkillServiceTest extends DaoIntergrationTest {
    private SkillService skillService;
    private JdbcTemplate jdbc;
    private DataSource dataSource;
    private FieldService fs;

    @Before
    public void setUp() throws Exception {
        dataSource = this.getDataSource();
        jdbc = new JdbcTemplate(dataSource);
        skillService = new SkillService(jdbc);
        fs = new FieldService(jdbc);

        Field testField = this.fs.getAllFields().get(0);
        Skill newSkill = new Skill();
        newSkill.setExperience(2);
        newSkill.setField(testField);
        newSkill.setSummary("Add skill test");
        this.skillService.addSkill(newSkill);
    }

    @Test
    public void getAllSkills() {
        List<Skill> listBefore = this.skillService.getAllSkills();
        this.skillService.deleteSkill(listBefore.get(0));
        List<Skill> listAfter = this.skillService.getAllSkills();

        assertEquals(listBefore.size() - 1, listAfter.size());

    }

    @Test
    public void getSkillsbyFieldId() {
        Field field = this.fs.getAllFields().get(0);

        List<Skill> listBefore = this.skillService.getSkillsbyFieldId(field.getField_id());
        Skill skillz = new Skill();
        skillz.setField(field);
        skillz.setExperience(66);
        skillz.setSummary("lorem ipsum");
        this.skillService.addSkill(skillz);

        List<Skill> listAfter = this.skillService.getSkillsbyFieldId(field.getField_id());
        assertEquals(listBefore.size() + 1, listAfter.size());
    }

    @Test
    public void getSkillById() {
        Skill expected = this.skillService.getAllSkills().get(0);
        Skill actual = this.skillService.getSkillById(expected.getSkill_id());

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void updateSkill() {
        Skill toChange =this.skillService.getAllSkills().get(0);
        toChange.setExperience(999);
        boolean updated = this.skillService.updateSkill(toChange);
        assertEquals(true, updated);

        Skill result = this.skillService.getSkillById(toChange.getSkill_id());
        assertEquals(toChange.getExperience(), result.getExperience());

    }

    @Test
    public void addSkill() {
        List<Skill> listBefore = this.skillService.getAllSkills();
        Field field = this.fs.getAllFields().get(0);
        Skill newSkill = new Skill();
        newSkill.setExperience(7);
        newSkill.setField(field);
        newSkill.setSummary("Add skill test");

        boolean result = this.skillService.addSkill(newSkill);

        List<Skill> listAfter = this.skillService.getAllSkills();

        assertEquals(true, result);
        assertEquals(listBefore.size() + 1, listAfter.size());
        System.out.println(listAfter.get(0).toString());

    }

    @Test
    public void deleteSkill() {
        List<Skill> listBefore = this.skillService.getAllSkills();
        boolean deleted = this.skillService.deleteSkill(listBefore.get(0));

        assertEquals(true, deleted);

        List<Skill> listAfter = this.skillService.getAllSkills();
        assertEquals(listBefore.size() - 1, listAfter.size());
    }
}