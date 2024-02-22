package src.main.java.com.school.schoolmanagement.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.main.java.com.school.schoolmanagement.models.*;

public class OfficeAssignmentDAL {

    private static OfficeAssignmentDAL instance;

    public static OfficeAssignmentDAL getInstance() {
        if (instance == null) {
            instance = new OfficeAssignmentDAL();
        }
        return instance;
    }

    private static OfficeAssignmentModel createOfficeAssignment(ResultSet rs) {
        try {
            int id = rs.getInt("InstructorID");
            String location = rs.getString("Location");
            Timestamp timestamp = rs.getTimestamp("Timestamp");
            return new OfficeAssignmentModel(id, location, timestamp);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<OfficeAssignmentModel> readDB() {
        ArrayList<OfficeAssignmentModel> officeAssignmentList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM OfficeAssignment")) {
            while (rs.next()) {
                OfficeAssignmentModel OfficeAssignmentModel = createOfficeAssignment(rs);
                officeAssignmentList.add(OfficeAssignmentModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return officeAssignmentList;
    }

    public int insert(OfficeAssignmentModel officeAssignment) {
        String insertSql = "INSERT INTO OfficeAssignment (InstructorID, Location, Timestamp) VALUES (?, ?, ?)";
        Object[] args = {
                officeAssignment.getInstructorID(),
                officeAssignment.getLocation(),
                officeAssignment.getTimestamp()
        };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(OfficeAssignmentModel officeAssignment) {
        String updateSql = "UPDATE OfficeAssignment SET Location = ?, Timestamp = ?  WHERE InstructorID = ?";
        Object[] args = {
                officeAssignment.getLocation(),
                officeAssignment.getTimestamp(),
                officeAssignment.getInstructorID()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from OfficeAssignment where InstructorID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<OfficeAssignmentModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT InstructorID, Location, Timestamp FROM OfficeAssignment WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(InstructorID, Location, Timestamp) LIKE ?");
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
                    List<OfficeAssignmentModel> officeAssignmentList = new ArrayList<>();
                    while (rs.next()) {
                        OfficeAssignmentModel officeAssignmentModel = createOfficeAssignment(rs);
                        officeAssignmentList.add(officeAssignmentModel);
                    }

                    if (officeAssignmentList.isEmpty()) {
                        throw new SQLException("No office assignment found!!!");
                    }

                    return officeAssignmentList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}