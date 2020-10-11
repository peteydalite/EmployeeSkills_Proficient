package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Skill {
    private UUID skill_id;
    private Field field;
    private int experience;
    private String summary;

    public Skill() {
    }


    public Skill(UUID skill_id, Field field, int experience, String summary) {
        this.skill_id = skill_id;
        this.field = field;
        this.experience = experience;
        this.summary = summary;
    }

    public UUID getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(UUID skill_id) {
        this.skill_id = skill_id;
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
                "skill_id=" + skill_id +
                ", field=" + field +
                ", experience=" + experience +
                ", summary='" + summary + '\'' +
                '}';

    }
}