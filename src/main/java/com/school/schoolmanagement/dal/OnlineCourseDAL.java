package com.school.schoolmanagement.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.models.*;

public class OnlineCourseDAL {

    private static OnlineCourseDAL instance;

    public static OnlineCourseDAL getInstance() {
        if (instance == null) {
            instance = new OnlineCourseDAL();
        }
        return instance;
    }

    private static OnlineCourseModel createOnlineCourse(ResultSet rs) {
        try {
            int id = rs.getInt("CourseID");

            String url = rs.getString("url");

            return new OnlineCourseModel(id, null, 0, 0, url);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<OnlineCourseModel> readDB() {
        ArrayList<OnlineCourseModel> onlineCourseList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM OnlineCourse")) {
            while (rs.next()) {
                OnlineCourseModel OnlineCourseModel = createOnlineCourse(rs);
                onlineCourseList.add(OnlineCourseModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return onlineCourseList;
    }

    public int insert(OnlineCourseModel onlineCourse) {
        String insertSql = "INSERT INTO OnlineCourse (CourseID, url) VALUES (?, ?)";
        Object[] args = {
                onlineCourse.getId(),
                onlineCourse.getUrl()
        };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(OnlineCourseModel onlineCourse) {
        String updateSql = "UPDATE OnlineCourse SET url = ? WHERE CourseID = ?";
        Object[] args = {
                onlineCourse.getUrl(),
                onlineCourse.getId()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from OnlineCourse where CourseID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<OnlineCourseModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT CourseID, url FROM OnlineCourse WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(CourseID, url) LIKE ?");
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
                    List<OnlineCourseModel> onlineCourseList = new ArrayList<>();
                    while (rs.next()) {
                        OnlineCourseModel onlineCourseModel = createOnlineCourse(rs);
                        onlineCourseList.add(onlineCourseModel);
                    }

                    if (onlineCourseList.isEmpty()) {
                        throw new SQLException("No online course found!!!");
                    }

                    return onlineCourseList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}