package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Field;

import java.util.List;
import java.util.UUID;

public interface FieldDao {
    List<Field> getAllFields();
    List<Field> getFieldsByType(String type);
    Field getFieldByName(String name);
    Field getFieldById(UUID field_id);
    boolean addField(Field field);
    boolean updateField(Field field);
    boolean deleteField(Field field);
}
