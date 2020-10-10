package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Field {
    private UUID field_id;
    private String name;
    private String type;

    public Field(){};

    public Field(UUID field_id, String name, String type) {
        this.field_id = field_id;
        this.name = name;
        this.type = type;
    }

    public UUID getField_id() {
        return field_id;
    }

    public void setField_id(UUID field_id) {
        this.field_id = field_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
