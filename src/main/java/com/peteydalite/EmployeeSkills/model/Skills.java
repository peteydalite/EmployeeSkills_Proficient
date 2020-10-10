package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Skills {
    private UUID skills_id;
    private int experience;
    private String summary;
    private UUID field_id;

    public Skills(){};

    public Skills(UUID skills_id, int experience, String summary, UUID field_id) {
        this.skills_id = skills_id;
        this.experience = experience;
        this.summary = summary;
        this.field_id = field_id;
    }

    public UUID getSkills_id() {
        return skills_id;
    }

    public void setSkills_id(UUID skills_id) {
        this.skills_id = skills_id;
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

    public UUID getField_id() {
        return field_id;
    }

    public void setField_id(UUID field_id) {
        this.field_id = field_id;
    }
}
