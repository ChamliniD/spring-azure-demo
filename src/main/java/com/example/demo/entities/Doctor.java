package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

@Entity
@Table(name = "HealthfirstDoctorSample")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue
    private int id;
    private String registeredNumber;
    private String name;
    private String specialization;

    private String email;

    private String photoName;

    public Doctor() {
    }

    public Doctor(int id, String registeredNumber, String name, String specialization) {
        this.id = id;
        this.registeredNumber = registeredNumber;
        this.name = name;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisteredNumber() {
        return registeredNumber;
    }

    public void setRegisteredNumber(String registeredNumber) {
        this.registeredNumber = registeredNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", registeredNumber='" + registeredNumber + '\'' +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", email='" + email + '\'' +
                ", photoName='" + photoName + '\'' +
                '}';
    }
}
