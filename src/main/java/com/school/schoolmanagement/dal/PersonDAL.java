package com.school.schoolmanagement.dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.school.schoolmanagement.models.*;

public class PersonDAL {

    private static PersonDAL instance;

    public static PersonDAL getInstance() {
        if (instance == null) {
            instance = new PersonDAL();
        }
        return instance;
    }

    private static PersonModel createPerson(ResultSet rs) {
        try {
            int id = rs.getInt("PersonID");
            String lastName = rs.getString("Lastname");
            String firstName = rs.getString("Firstname");
            Date hireDate = rs.getDate("HireDate");
            Date enrollmentDate = rs.getDate("EnrollmentDate");
            return new PersonModel(id, lastName, firstName, hireDate, enrollmentDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PersonModel> readDB() {
        ArrayList<PersonModel> personList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM Person")) {
            while (rs.next()) {
                PersonModel PersonModel = createPerson(rs);
                personList.add(PersonModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public int insert(PersonModel person) {
        String insertSql = "INSERT INTO Person (PersonID, Lastname, Firstname, HireDate, EnrollmentDate) VALUES (?, ?, ?, ?)";
        Object[] args = {
                person.getPersonID(),
                person.getLastName(),
                person.getFirstName(),
                person.getHireDate(),
                person.getEnrollmentDate()
        };
        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(PersonModel person) {
        String updateSql = "UPDATE Person SET Lastname = ?, Firstname = ?, HireDate = ?, EnrollmentDate = ? WHERE PersonID = ?";
        Object[] args = {
                person.getLastName(),
                person.getFirstName(),
                person.getHireDate(),
                person.getEnrollmentDate(),
                person.getPersonID()
        };
        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int delete(int id) {
        String updateStatusSql = "DELETE FROM person WHERE PersonID = ?";
        Object[] args = { id };
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<PersonModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder(
                    "SELECT PersonID, Lastname, Firstname, HireDate, EnrollmentDate FROM Person WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(PersonID, Lastname, Firstname, HireDate, EnrollmentDate) LIKE ?");
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
                    List<PersonModel> personList = new ArrayList<>();
                    while (rs.next()) {
                        PersonModel personModel = createPerson(rs);
                        personList.add(personModel);
                    }

                    if (personList.isEmpty()) {
                        throw new SQLException("No persons found!!!!");
                    }

                    return personList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}