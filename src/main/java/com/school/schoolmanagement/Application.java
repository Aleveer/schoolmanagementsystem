package src.main.java.com.school.schoolmanagement;

import src.main.java.com.school.schoolmanagement.bus.CourseBUS;
import src.main.java.com.school.schoolmanagement.models.CourseModel;

public class Application {
    public static void main(String[] args) {
        for (CourseModel course : CourseBUS.getInstance().getAllModels()) {
            System.out.println(course);
        }
    }
}
