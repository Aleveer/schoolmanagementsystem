package Main;

import BUS.CourseBUS;
import Model.CourseModel;

public class main {
    public static void main(String[] args) {
        for(CourseModel course : CourseBUS.getInstance().getAllModels()) {
            System.out.println(course);
        }
    }
}
