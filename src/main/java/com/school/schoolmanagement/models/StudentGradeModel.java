package src.main.java.com.school.schoolmanagement.models;

import java.math.BigDecimal;

public class StudentGradeModel {
    private int enrollmentID,courseID,studentID;
    private BigDecimal grade;

    public StudentGradeModel(int enrollmentID, int courseID, int studentID, BigDecimal grade) {
        this.enrollmentID = enrollmentID;
        this.courseID = courseID;
        this.studentID = studentID;
        this.grade = grade;
    }

    public int getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(int enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "StudentGradeModel{" +
                "enrollmentID=" + enrollmentID +
                ", courseID=" + courseID +
                ", studentID=" + studentID +
                ", grade=" + grade +
                '}';
    }
}
