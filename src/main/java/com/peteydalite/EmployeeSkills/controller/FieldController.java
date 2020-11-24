package com.peteydalite.EmployeeSkills.controller;

import com.peteydalite.EmployeeSkills.dao.FieldDao;
import com.peteydalite.EmployeeSkills.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class FieldController {

    @Autowired
    FieldDao fieldDao;

    public FieldController(FieldDao fieldDao){
        this.fieldDao = fieldDao;
    }

    @RequestMapping(path="/fields", method = RequestMethod.GET)
    public List<Field> getAllFields(){
        return this.fieldDao.getAllFields();
    }
}
