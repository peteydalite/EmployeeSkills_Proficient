package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.model.Field;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class FieldServiceTest extends DaoIntergrationTest{

    private FieldService fieldService;
    private JdbcTemplate jdbc;
    private DataSource dataSource;

    @Before
    public void setup(){
        dataSource = this.getDataSource();
        jdbc = new JdbcTemplate(dataSource);
        fieldService = new FieldService(jdbc);

        Field test = new Field();
        test.setType("TEST");
        test.setName("Test Field");
        this.fieldService.addField(test);

    }
    @Test
    public void getAllFields() {
        List<Field> listBefore = this.fieldService.getAllFields();
        Field newField = new Field();
        newField.setType("Peter's Test");
        newField.setName("Test Field");
        this.fieldService.addField(newField);
        List<Field> listAfter = this.fieldService.getAllFields();
        listAfter.forEach(field -> {
            System.out.println(field.toString());
        });

        assertEquals(listBefore.size() + 1, listAfter.size());

    }

    @Test
    public void getFieldsByType() {
        List<Field> fields = this.fieldService.getFieldsByType("TEST");

        assertEquals("Test Field", fields.get(0).getName().trim());
    }

    @Test
    public void getFieldByName() {
        Field result = this.fieldService.getFieldByName("Test Field");

        assertEquals("TEST", result.getType().trim());
    }

    @Test
    public void getFieldById() {
        List<Field> fields = this.fieldService.getAllFields();
        UUID id = fields.get(0).getId();
        Field result = this.fieldService.getFieldById(id);

        assertEquals(id, result.getId());
    }

    @Test
    public void addField() {
        Field newField = new Field();
        newField.setType("Software Development");
        newField.setName("Java");
        boolean result = this.fieldService.addField(newField);

        assertEquals(true, result);
    }

    @Test
    public void updateField() {
        List<Field> fields = this.fieldService.getAllFields();
        Field toChange = fields.get(0);
        toChange.setName("I updated this field");
        boolean updated = this.fieldService.updateField(toChange);

        assertEquals(true, updated);

        Field result = this.fieldService.getFieldById(toChange.getId());

        assertEquals(toChange.getName(), result.getName());
    }

    @Test
    public void deleteField() {
        List<Field> listBefore = this.fieldService.getAllFields();
        boolean deleted = this.fieldService.deleteField(listBefore.get(0));
        List<Field> listAfter = this.fieldService.getAllFields();

        assertEquals(true, deleted);

        assertEquals(listBefore.size() - 1, listAfter.size());

    }
}