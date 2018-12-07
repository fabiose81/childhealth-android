package com.fabiose.childhealth.domains;

import java.io.Serializable;
import java.util.Date;


public class Child implements Serializable {

    private Integer id;
    private String name;
    private Date dateBirth;
    private String motherName;
    private String fatherName;
    private String photo;

    public Child(Integer id, String name, Date dateBirth, String motherName, String fatherName) {
        this.id = id;
        this.name = name;
        this.dateBirth = dateBirth;
        this.motherName = motherName;
        this.fatherName = fatherName;
    }

    public Child(String name, Date dateBirth, String motherName, String fatherName, String photo) {
        this.name = name;
        this.dateBirth = dateBirth;
        this.motherName = motherName;
        this.fatherName = fatherName;
        this.photo = photo;
    }

    public Child() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
