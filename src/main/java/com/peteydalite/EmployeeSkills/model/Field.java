package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Field {
    private UUID id;
    private String name;
    private String type;

    public Field(){};

    public Field(UUID id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID field_id) {
        this.id = field_id;
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

    @Override
    public String toString() {
        return "Field{" +
                "field_id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
