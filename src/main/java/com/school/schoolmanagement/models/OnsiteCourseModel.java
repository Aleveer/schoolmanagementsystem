package src.main.java.com.school.schoolmanagement.models;

import java.sql.Time;

public class OnsiteCourseModel extends CourseModel{
    private String location,days;
    private Time time;

    public OnsiteCourseModel(int id, String title, int credit, int departmentID, String location, String days,Time time) {
        super(id, title, credit, departmentID);
        this.location = location;
        this.days = days;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OnsiteCourseModel{" +
                "location='" + location + '\'' +
                ", days='" + days + '\'' +
                ", time=" + time +
                '}';
    }
}
