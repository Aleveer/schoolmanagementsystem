package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.CourseInstructorDAL;
import Model.CourseInstructorModel;

public class CourseInstructorBUS{
    private final List<CourseInstructorModel> courseInstructorList = new ArrayList<>();
    private static CourseInstructorBUS instance;

    private CourseInstructorBUS() {
        this.courseInstructorList.addAll(CourseInstructorDAL.getInstance().readDB());
    }

    public static CourseInstructorBUS getInstance() {
        if (instance == null) {
            instance = new CourseInstructorBUS();
        }
        return instance;
    }

    public void refresh() {
        courseInstructorList.clear();
        courseInstructorList.addAll(CourseInstructorDAL.getInstance().readDB());
    }

    public List<CourseInstructorModel> getAllModels() {
        return Collections.unmodifiableList(courseInstructorList);
    }


    public CourseInstructorModel getModelById(int id) {
        refresh();
        for (CourseInstructorModel CourseInstructorModel : courseInstructorList) {
            if (CourseInstructorModel.getCourseID() == id) {
                return CourseInstructorModel;
            }
        }
        return null;
    }

    public int addModel(CourseInstructorModel CourseInstructorModel) {
        if(CourseInstructorModel.getCourseID() <= 0
                || CourseInstructorModel.getPersonID() <= 0) {
            throw new IllegalArgumentException("error infomation, try again!!!");
        }

        int result = CourseInstructorDAL.getInstance().insert(CourseInstructorModel);
        if(result > 0) {
            courseInstructorList.add(CourseInstructorModel);
            return result;
        }
        return -1;
    }

    public int updateModel(CourseInstructorModel CourseInstructorModel) {
        int result = CourseInstructorDAL.getInstance().update(CourseInstructorModel);
        if (result > 0) {
            int index = courseInstructorList.indexOf(CourseInstructorModel);
            if (index != -1) {
                courseInstructorList.set(index, CourseInstructorModel);
            }
        }
        return result;
    }

    public int deleteModel(int id,int personID) {
        CourseInstructorModel CourseInstructorModel = getModelById(id);

        int result = CourseInstructorDAL.getInstance().delete(id,personID);
        if (result > 0) {
            courseInstructorList.remove(CourseInstructorModel);
        }else {
            throw new IllegalArgumentException("Invalid id: "+id);
        }
        return result;
    }

    public List<CourseInstructorModel> searchModel(String value, String[] columns) {
        List<CourseInstructorModel> items = getAllModels();
        List<CourseInstructorModel> results = new ArrayList<>();
        for (CourseInstructorModel item : items) {
            if(checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(CourseInstructorModel CourseInstructorModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "courseid":
                    if (Integer.parseInt(value) == CourseInstructorModel.getCourseID()) {
                        return true;
                    }
                    break;
                case "personid":
                    if (Integer.parseInt(value) == CourseInstructorModel.getPersonID()) {
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