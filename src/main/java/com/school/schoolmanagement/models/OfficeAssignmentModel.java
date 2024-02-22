package main.java.com.school.schoolmanagement.models;

import java.sql.Timestamp;

public class OfficeAssignmentModel {
    private int instructorID;
    private String location;
    private Timestamp timestamp;

    public OfficeAssignmentModel(int instructorID, String location, Timestamp timestamp) {
        this.instructorID = instructorID;
        this.location = location;
        this.timestamp = timestamp;
    }

    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "OfficeAssignmentModel{" +
                "instructorID=" + instructorID +
                ", location='" + location + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
