package com.school.schoolmanagement.models;

import java.sql.Date;

public class PersonModel {
    private int personID;
    private String lastName,firstName;
    private Date hireDate,enrollmentDate;

    public PersonModel(int personID, String lastName, String firstName, Date hireDate, Date enrollmentDate) {
        this.personID = personID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.hireDate = hireDate;
        this.enrollmentDate = enrollmentDate;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }


}
