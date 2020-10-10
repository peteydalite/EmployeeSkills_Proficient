package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class EmployeeToSkills {
    private UUID employee_id;
    private UUID skills_id;

    public EmployeeToSkills(){};

    public EmployeeToSkills(UUID employee_id, UUID skills_id) {
        this.employee_id = employee_id;
        this.skills_id = skills_id;
    }

    public UUID getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(UUID employee_id) {
        this.employee_id = employee_id;
    }

    public UUID getSkills_id() {
        return skills_id;
    }

    public void setSkills_id(UUID skills_id) {
        this.skills_id = skills_id;
    }
}
