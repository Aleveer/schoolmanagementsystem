package com.school.schoolmanagement.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.models.*;

public class CourseDAL {

    private static CourseDAL instance;

    public static CourseDAL getInstance() {
        if (instance == null) {
            instance = new CourseDAL();
        }
        return instance;
    }

    private static CourseModel createCourseModel(ResultSet rs) {
        try {
            int id = rs.getInt("CourseID");
            String title = rs.getString("Title");
            int credit = rs.getInt("Credits");
            int departmentID = rs.getInt("departmentID");
            return new CourseModel(id, title, credit, departmentID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CourseModel> readDB() {
        ArrayList<CourseModel> courseList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("select * from Course")) {
            while (rs.next()) {
                CourseModel courseModel = createCourseModel(rs);
                courseList.add(courseModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public int insert(CourseModel course) {
        String insertSql = "INSERT INTO Course (CourseID, Title, Credits, DepartmentID) VALUES (?, ?, ?, ?)";
        Object[] args = { course.getId(), course.getTitle(), course.getCredit(), course.getDepartmentID() };
        System.out.println("hehe: " + insertSql + " : " + Arrays.toString(args));
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(CourseModel course) {
        String updateSql = "UPDATE Course SET Title = ?, Credits = ?, DepartmentID = ? WHERE CourseID = ?";
        Object[] args = { course.getTitle(), course.getCredit(), course.getDepartmentID(), course.getId() };

        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from Course where CourseID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<CourseModel> search(String condition, String[] columnNames) {
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Course");

            if (columnNames == null || columnNames.length == 0) {
                queryBuilder.append("CONCAT(CourseID, Title, Credits, DepartmentID) LIKE ?");
            } else if (columnNames.length == 1) { // specifical column
                queryBuilder.append(columnNames[0]).append(" LIKE ?");
            } else { // specifical columns
                queryBuilder.append("CONCAT(").append(String.join(", ", columnNames)).append(") LIKE ?");
            }

            String query = queryBuilder.toString();

            try (PreparedStatement pst = DatabaseConnect.getPreparedStatement(query, "%" + condition + "%")) {
                try (ResultSet rs = pst.executeQuery()) {
                    List<CourseModel> courseList = new ArrayList<>();
                    while (rs.next()) {
                        CourseModel courseModel = createCourseModel(rs);
                        courseList.add(courseModel);
                    }

                    if (courseList.isEmpty()) {
                        throw new SQLException("No course found!!");
                    }

                    return courseList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}