package com.fabiose.childhealth.domains;

import java.io.Serializable;
import java.util.Date;

public class VaccineHistory implements Serializable {

    private Integer id;
    private float weight;
    private int height;
    private String comment;
    private Child child;
    private Vaccine vaccine;
    private Date date;


    public VaccineHistory(Integer id, float weight, int height, String comment, Child child, Vaccine vaccine, Date date) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.comment = comment;
        this.child = child;
        this.vaccine = vaccine;
        this.date = date;
    }

    public VaccineHistory(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
