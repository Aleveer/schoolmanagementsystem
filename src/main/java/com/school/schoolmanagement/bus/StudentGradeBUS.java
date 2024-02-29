package com.school.schoolmanagement.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.dal.StudentGradeDAL;
import com.school.schoolmanagement.models.CourseModel;
import com.school.schoolmanagement.models.PersonModel;
import com.school.schoolmanagement.models.StudentGradeModel;

import javax.swing.*;

public class StudentGradeBUS {
    private final List<StudentGradeModel> studentGradeList = new ArrayList<>();
    private static StudentGradeBUS instance;

    private StudentGradeBUS() {
        this.studentGradeList.addAll(StudentGradeDAL.getInstance().readDB());
    }

    public static StudentGradeBUS getInstance() {
        if (instance == null) {
            instance = new StudentGradeBUS();
        }
        return instance;
    }

    public void refresh() {
        studentGradeList.clear();
        studentGradeList.addAll(StudentGradeDAL.getInstance().readDB());
    }

    public List<StudentGradeModel> getAllModels() {
        return Collections.unmodifiableList(studentGradeList);
    }

    public StudentGradeModel getModelById(int id) {
        refresh();
        for (StudentGradeModel StudentGradeModel : studentGradeList) {
            if (StudentGradeModel.getEnrollmentID() == id) {
                return StudentGradeModel;
            }
        }
        return null;
    }

    public int getMax() {
        refresh();
        int count = 0;
        for(StudentGradeModel person : studentGradeList) {
            count++;
        }
        return count + 1;
    }

    public int addModel(StudentGradeModel StudentGradeModel) {
        if (StudentGradeModel.getCourseID() <= 0
                || StudentGradeModel.getEnrollmentID() <= 0
                || StudentGradeModel.getStudentID() <= 0
                || StudentGradeModel.getGrade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Wrong information, try again!!!");
        }

        for (StudentGradeModel student : studentGradeList) {
            if (student.getCourseID() == StudentGradeModel.getCourseID() &&
                    student.getStudentID() == StudentGradeModel.getStudentID()) {
                throw new IllegalArgumentException("Course has existed");
            }
        }
        int result = StudentGradeDAL.getInstance().insert(StudentGradeModel);
        if (result > 0) {
            studentGradeList.add(StudentGradeModel);
            return result;
        }
        return -1;
    }

    public int updateModel(StudentGradeModel StudentGradeModel) {
        int result = StudentGradeDAL.getInstance().update(StudentGradeModel);
        if (result > 0) {
            int index = studentGradeList.indexOf(StudentGradeModel);
            if (index != -1) {
                studentGradeList.set(index, StudentGradeModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        StudentGradeModel StudentGradeModel = getModelById(id);

        int result = StudentGradeDAL.getInstance().delete(id);
        if (result > 0) {
            studentGradeList.remove(StudentGradeModel);
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        return result;
    }

    public List<StudentGradeModel> searchModel(String value, String[] columns) {
        List<StudentGradeModel> items = getAllModels();
        List<StudentGradeModel> results = new ArrayList<>();
        for (StudentGradeModel item : items) {
            if (checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(StudentGradeModel StudentGradeModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "courseid":
                    if (Integer.parseInt(value) == StudentGradeModel.getCourseID()) {
                        return true;
                    }
                    break;
                case "studentid":
                    if (Integer.parseInt(value) == StudentGradeModel.getStudentID()) {
                        return true;
                    }
                    break;
                case "enrollmentid":
                    if (Integer.parseInt(value) == StudentGradeModel.getEnrollmentID()) {
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