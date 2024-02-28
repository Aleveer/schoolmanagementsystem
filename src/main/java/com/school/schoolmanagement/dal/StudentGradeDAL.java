package com.school.schoolmanagement.dal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.models.*;

public class StudentGradeDAL {

    private static StudentGradeDAL instance;

    public static StudentGradeDAL getInstance() {
        if (instance == null) {
            instance = new StudentGradeDAL();
        }
        return instance;
    }

    private static StudentGradeModel createStudentGrade(ResultSet rs) {
        try {
            int id = rs.getInt("EnrollmentID");
            int courseID = rs.getInt("CourseID");
            int studentID = rs.getInt("StudentID");
            BigDecimal grade = rs.getBigDecimal("Grade");
            return new StudentGradeModel(id, courseID, studentID, grade);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<StudentGradeModel> readDB() {
        ArrayList<StudentGradeModel> studentGradeList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM StudentGrade")) {
            while (rs.next()) {
                StudentGradeModel StudentGradeModel = createStudentGrade(rs);
                studentGradeList.add(StudentGradeModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentGradeList;
    }

    public int insert(StudentGradeModel studentGrade) {
        System.out.println(studentGrade);
        String insertSql = "INSERT INTO StudentGrade (EnrollmentID, CourseID, StudentID, Grade) VALUES (?, ?, ?,?)";
        Object[] args = {
                studentGrade.getEnrollmentID(),
                studentGrade.getCourseID(),
                studentGrade.getStudentID(),
                studentGrade.getGrade()
        };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(StudentGradeModel studentGrade) {
        String updateSql = "UPDATE StudentGrade SET CourseID = ?, StudentID = ?, Grade = ? WHERE EnrollmentID = ?";
        Object[] args = {
                studentGrade.getCourseID(),
                studentGrade.getStudentID(),
                studentGrade.getGrade(),
                studentGrade.getEnrollmentID()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from StudentGrade where EnrollmentID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<StudentGradeModel> search(String condition, String[] columnNames) {
        try {
            String query;
            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                query = "SELECT EnrollmentID, CourseID, StudentID, Grade FROM StudentGrade WHERE CONCAT(EnrollmentID, CourseID, StudentID, Grade) LIKE ?";
            } else if (columnNames.length == 1) {
                // Search specific column
                String column = columnNames[0];
                query = "SELECT EnrollmentID, CourseID, StudentID, Grade FROM StudentGrade WHERE " + column + " LIKE ?";
            } else {
                // Search specific columns
                query = "SELECT EnrollmentID, CourseID, StudentID, Grade FROM StudentGrade WHERE CONCAT("
                        + String.join(", ", columnNames) +
                        ") LIKE ?";
            }

            try (
                    PreparedStatement pst = DatabaseConnect.getPreparedStatement(
                            query,
                            "%" + condition + "%")) {
                try (ResultSet rs = pst.executeQuery()) {
                    List<StudentGradeModel> studentGradeList = new ArrayList<>();
                    while (rs.next()) {
                        StudentGradeModel studentGradeModel = createStudentGrade(rs);
                        studentGradeList.add(studentGradeModel);
                    }

                    if (studentGradeList.isEmpty()) {
                        throw new SQLException("No student grade found!!!");
                    }

                    return studentGradeList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}