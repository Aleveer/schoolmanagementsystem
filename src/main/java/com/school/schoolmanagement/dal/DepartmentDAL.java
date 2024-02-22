package src.main.java.com.school.schoolmanagement.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.main.java.com.school.schoolmanagement.models.*;

public class DepartmentDAL {

    private static DepartmentDAL instance;

    public static DepartmentDAL getInstance() {
        if (instance == null) {
            instance = new DepartmentDAL();
        }
        return instance;
    }

    private static DepartmentModel createDepartmentModel(ResultSet rs) {
        try {
            int id = rs.getInt("DepartmentID");
            String name = rs.getString("Name");
            double budget = rs.getDouble("Budget");
            Timestamp startDate = rs.getTimestamp("StartDate");
            int administrator = rs.getInt("Administrator");
            return new DepartmentModel(id, name, budget, startDate, administrator);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<DepartmentModel> readDB() {
        ArrayList<DepartmentModel> departmentList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM Department")) {
            while (rs.next()) {
                DepartmentModel DepartmentModel = createDepartmentModel(rs);
                departmentList.add(DepartmentModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    public int insert(DepartmentModel department) {
        String insertSql = "INSERT INTO Department (DepartmentID, Name, Budget, StartDate, Administrator) VALUES (?, ?, ?, ?, ?)";
        Object[] args = { department.getDepartmentID(), department.getName(), department.getBudget(),
                department.getStartDate(), department.getAdministrator() };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(DepartmentModel department) {
        String updateSql = "UPDATE users SET Name = ?, Budget = ?, StartDate = ?, Administrator = ? WHERE DepartmentID = ?";
        Object[] args = {
                department.getName(),
                department.getBudget(),
                department.getStartDate(),
                department.getAdministrator(),
                department.getDepartmentID()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "delete from Department where DepartmentID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<DepartmentModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT DepartmentID, Name, Budget, StartDate, Administrator FROM Department WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(DepartmentID, Name, Budget, StartDate, Administrator) LIKE ?");
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
                    List<DepartmentModel> departmentList = new ArrayList<>();
                    while (rs.next()) {
                        DepartmentModel departmentModel = createDepartmentModel(rs);
                        departmentList.add(departmentModel);
                    }

                    if (departmentList.isEmpty()) {
                        throw new SQLException("No department found!!!");
                    }

                    return departmentList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}