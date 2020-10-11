package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.dao.FieldDao;
import com.peteydalite.EmployeeSkills.model.Field;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FieldService implements FieldDao {

    private JdbcTemplate jdbc;

    public FieldService(JdbcTemplate jdbcTemplate){
        this.jdbc = jdbcTemplate;
    }

    private Field mapRowToField(SqlRowSet rs){
        Field field = new Field();
        field.setField_id((java.util.UUID) rs.getObject("field_id"));
        field.setName(rs.getString("name"));
        field.setType(rs.getString("type"));

        return field;
    }
    @Override
    public List<Field> getAllFields() {
        List<Field> allFields = new ArrayList<>();
        String sqlSelect = "Select * from Fields ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect);
            while(result.next()){
                Field field = mapRowToField(result);
                allFields.add(field);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return allFields;
    }

    @Override
    public List<Field> getFieldsByType(String type) {
        List<Field> fieldsByType = new ArrayList<>();
        String sqlSelect = "Select * from Fields where type = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, type);
            while(result.next()){
                Field field = mapRowToField(result);
                fieldsByType.add(field);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return fieldsByType;
    }

    @Override
    public Field getFieldByName(String name) {
        Field field = null;
        String sqlSelect = "Select * from Fields where name = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, name);
            if(result.next()){
                field = mapRowToField(result);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return field;
    }

    @Override
    public Field getFieldById(UUID field_id) {
        Field field = null;
        String sqlSelect = "Select * from Fields where field_id = ? ";
        try{
            SqlRowSet result = this.jdbc.queryForRowSet(sqlSelect, field_id);
            if(result.next()){
                field = mapRowToField(result);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return field;
    }

    @Override
    public boolean addField(Field field) {
        boolean added = false;
        String sqlInsert = "Insert into Fields values (?,?,?) ";

        try {
            added = this.jdbc.update(sqlInsert,UUID.randomUUID(), field.getName(), field.getType()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return added;
    }

    @Override
    public boolean updateField(Field field) {
        boolean updated = false;
        String sqlUpdated = "Update Fields set name = ?, type = ? Where field_id = ? ";
        try {
            updated = this.jdbc.update(sqlUpdated, field.getName(), field.getType(), field.getField_id()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return updated;
    }

    @Override
    public boolean deleteField(Field field) {
        boolean deleted = false;
        String delete = "Delete from Fields where field_id = ? ";
        try{
            deleted = this.jdbc.update(delete, field.getField_id()) == 1;
        }catch (Exception e){
            System.out.println(e);
        }
        return deleted;
    }
}
