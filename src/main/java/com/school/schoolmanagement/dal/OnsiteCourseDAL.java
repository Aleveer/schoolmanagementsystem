package com.school.schoolmanagement.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.models.*;

public class OnsiteCourseDAL {

    private static OnsiteCourseDAL instance;

    public static OnsiteCourseDAL getInstance() {
        if (instance == null) {
            instance = new OnsiteCourseDAL();
        }
        return instance;
    }

    private static OnsiteCourseModel createOnsiteCourse(ResultSet rs) {
        try {
            int id = rs.getInt("CourseID");
            String title = rs.getString("Title");
            int credits = rs.getInt("Credits");
            int departmentID = rs.getInt("DepartmentID");

            String location = rs.getString("Location");
            String days = rs.getString("Days");
            Time time = rs.getTime("Time");
            return new OnsiteCourseModel(id, title, credits, departmentID, location, days, time);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<OnsiteCourseModel> readDB() {
        ArrayList<OnsiteCourseModel> onsiteCourseList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM OnsiteCourse, Course Where OnsiteCourse.CourseID = Course.CourseID")) {
            while (rs.next()) {
                OnsiteCourseModel OnsiteCourseModel = createOnsiteCourse(rs);
                onsiteCourseList.add(OnsiteCourseModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return onsiteCourseList;
    }

    public int insert(OnsiteCourseModel onsiteCourse) {
        String insertSql = "INSERT INTO OnsiteCourse (CourseID, Location, Days, Time) VALUES (?, ?, ?, ?)";
        Object[] args = {
                onsiteCourse.getId(),
                onsiteCourse.getLocation(),
                onsiteCourse.getDays(),
                onsiteCourse.getTime()
        };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(OnsiteCourseModel onsiteCourse) {
        String updateSql = "UPDATE OnsiteCourse SET Location = ?, Days = ?, Time = ? WHERE CourseID = ?";
        Object[] args = {
                onsiteCourse.getLocation(),
                onsiteCourse.getDays(),
                onsiteCourse.getTime(),
                onsiteCourse.getId()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from OnsiteCourse where CourseID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<OnsiteCourseModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT CourseID, Location, Days, Time FROM OnsiteCourse WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(CourseID, Location, Days, Time) LIKE ?");
            } else if (columnNames.length == 1) {
                // Search specific column
                queryBuilder.append(columnNames[0]).append(" LIKE ?");
            } else {
                // Search specific columns
                queryBuilder.append("CONCAT(").append(String.join(", ", columnNames)).append(") LIKE ?");
            }

            String query = queryBuilder.toString();

            try (PreparedStatement pst = DatabaseConnect.getPreparedStatement(query, "%" + condition + "%")) {
                try (ResultSet rs = pst.executeQuery()) {
                    List<OnsiteCourseModel> onsiteCourseList = new ArrayList<>();
                    while (rs.next()) {
                        OnsiteCourseModel onsiteCourseModel = createOnsiteCourse(rs);
                        onsiteCourseList.add(onsiteCourseModel);
                    }

                    if (onsiteCourseList.isEmpty()) {
                        throw new SQLException("No onsite course found!!!");
                    }

                    return onsiteCourseList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}