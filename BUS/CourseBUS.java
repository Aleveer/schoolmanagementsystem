package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.CourseDAL;
import Model.CourseModel;

import javax.swing.*;

public class CourseBUS{
    private final List<CourseModel> courseList = new ArrayList<>();
    private static CourseBUS instance;

    private CourseBUS() {
        this.courseList.addAll(CourseDAL.getInstance().readDB());
    }

    public static CourseBUS getInstance() {
        if (instance == null) {
            instance = new CourseBUS();
        }
        return instance;
    }

    public void refresh() {
        courseList.clear();
        courseList.addAll(CourseDAL.getInstance().readDB());
    }

    public List<CourseModel> getAllModels() {
        return Collections.unmodifiableList(courseList);
    }


    public CourseModel getModelById(int id) {
        refresh();
        for (CourseModel CourseModel : courseList) {
            if (CourseModel.getId() == id) {
                return CourseModel;
            }
        }
        return null;
    }

    public int addModel(CourseModel CourseModel) {
        if(CourseModel.getTitle().isEmpty() || CourseModel.getTitle() == null
                || CourseModel.getCredit() <= 0
                || CourseModel.getDepartmentID() <= 0) {
            throw new IllegalArgumentException("error infomation, try again!!!");
        }

        for(CourseModel course : CourseBUS.getInstance().getAllModels()) {
            if(CourseModel.getId() == course.getId()) {
                JOptionPane.showMessageDialog(null, "Course ID has existed");
                break;
            }else if(CourseModel.getTitle().equals(course.getTitle())) {
                JOptionPane.showMessageDialog(null, "Title has existed");
                break;
            }
        }
        System.out.println("Test: "+CourseModel);
        int result = CourseDAL.getInstance().insert(CourseModel);
        if(result > 0) {
            courseList.add(CourseModel);
            return result;
        }
        return -1;
    }

    public int updateModel(CourseModel CourseModel) {
        int result = CourseDAL.getInstance().update(CourseModel);
        if (result > 0) {
            int index = courseList.indexOf(CourseModel);
            if (index != -1) {
                courseList.set(index, CourseModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        CourseModel CourseModel = getModelById(id);

        int result = CourseDAL.getInstance().delete(id);
        if (result > 0) {
            courseList.remove(CourseModel);
        }else {
            throw new IllegalArgumentException("Invalid id: "+id);
        }
        return result;
    }

    public List<CourseModel> searchModel(String value, String[] columns) {
        List<CourseModel> items = getAllModels();
        List<CourseModel> results = new ArrayList<>();
        for (CourseModel item : items) {
            if(checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(CourseModel courseModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "id":
                    if (Integer.parseInt(value) == courseModel.getId()) {
                        return true;
                    }
                    break;
                case "title":
                    if (courseModel.getTitle().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "credits":
                    if (Integer.parseInt(value) == courseModel.getCredit()) {
                        return true;
                    }
                    break;
                case "departmentid":
                    if (Integer.parseInt(value) == courseModel.getDepartmentID()) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

}