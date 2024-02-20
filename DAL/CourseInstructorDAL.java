package DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.CourseInstructorModel;

public class CourseInstructorDAL {
    private static CourseInstructorDAL instance;

    public static CourseInstructorDAL getInstance() {
        if (instance == null) {
            instance = new CourseInstructorDAL();
        }
        return instance;
    }

    private static CourseInstructorModel createCourseInstructor(ResultSet rs){
        try {
            int CourseID = rs.getInt("CourseID");
            int PersonID = rs.getInt("PersonID");
            return new CourseInstructorModel(CourseID,PersonID);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<CourseInstructorModel> readDB() {
        ArrayList<CourseInstructorModel> courseInstructorList = new ArrayList<>();
        try (
                ResultSet rs = DatabaseConnect.executeQuery("SELECT * FROM CourseInstructor")) {
            while (rs.next()) {
                CourseInstructorModel courseInstructor = createCourseInstructor(rs);
                courseInstructorList.add(courseInstructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseInstructorList;
    }

    public int insert(CourseInstructorModel courseInstructor) {
        String insertSql = "INSERT INTO CourseInstructor (CourseID, PersonID) VALUES (?, ?)";
        Object[] args = {courseInstructor.getCourseID(),courseInstructor.getPersonID(),};

        try {
            return DatabaseConnect.executeUpdate(insertSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int update(CourseInstructorModel course) {
        String updateSql = "UPDATE CourseInstructor SET PersonID = ? WHERE CourseID = ?";
        Object[] args = {course.getPersonID(),course.getCourseID(),};

        try {
            return DatabaseConnect.executeUpdate(updateSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int delete(int id,int personID) {
        String updateStatusSql = "delete from CourseInstructor where CourseID = ? and PersonID = ?";
        Object[] args = { id , personID};
        try {
            return DatabaseConnect.executeUpdate(updateStatusSql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<CourseInstructorModel> search(String condition, String[] columnNames) {
        try {
            if (condition == null || condition.trim().isEmpty()) {
                throw new IllegalArgumentException("Search condition cannot be empty or null");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT CourseID, PersonID FROM CourseInstructor WHERE ");

            if (columnNames == null || columnNames.length == 0) {
                // Search all columns
                queryBuilder.append("CONCAT(CourseID, PersonID) LIKE ?");
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
                    List<CourseInstructorModel> courseInstructorList = new ArrayList<>();
                    while (rs.next()) {
                        CourseInstructorModel courseInstructorModel = createCourseInstructor(rs);
                        courseInstructorList.add(courseInstructorModel);
                    }

                    if (courseInstructorList.isEmpty()) {
                        throw new SQLException("No course instructor found!!!");
                    }

                    return courseInstructorList;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
