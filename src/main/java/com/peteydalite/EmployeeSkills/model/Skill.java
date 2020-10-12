package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Skill {
    private UUID id;
    private Field field;
    private int experience;
    private String summary;

    public Skill() {
    }


    public Skill(UUID id, Field field, int experience, String summary) {
        this.id = id;
        this.field = field;
        this.experience = experience;
        this.summary = summary;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skill_id=" + id +
                ", field=" + field +
                ", experience=" + experience +
                ", summary='" + summary + '\'' +
                '}';

    }
}