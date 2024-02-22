package main.java.com.school.schoolmanagement.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.com.school.schoolmanagement.dal.OnlineCourseDAL;
import main.java.com.school.schoolmanagement.models.OnlineCourseModel;

public class OnlineCourseBUS {
    private final List<OnlineCourseModel> onlineCourseList = new ArrayList<>();
    private static OnlineCourseBUS instance;

    private OnlineCourseBUS() {
        this.onlineCourseList.addAll(OnlineCourseDAL.getInstance().readDB());
    }

    public static OnlineCourseBUS getInstance() {
        if (instance == null) {
            instance = new OnlineCourseBUS();
        }
        return instance;
    }

    public void refresh() {
        onlineCourseList.clear();
        onlineCourseList.addAll(OnlineCourseDAL.getInstance().readDB());
    }

    public List<OnlineCourseModel> getAllModels() {
        return Collections.unmodifiableList(onlineCourseList);
    }

    public OnlineCourseModel getModelById(int id) {
        refresh();
        for (OnlineCourseModel OnlineCourseModel : onlineCourseList) {
            if (OnlineCourseModel.getId() == id) {
                return OnlineCourseModel;
            }
        }
        return null;
    }

    public int addModel(OnlineCourseModel OnlineCourseModel) {
        if (OnlineCourseModel.getId() <= 0
                || OnlineCourseModel.getUrl().isEmpty() || OnlineCourseModel.getUrl() == null) {
            throw new IllegalArgumentException("Wrong information, try again!!!");
        }

        int result = OnlineCourseDAL.getInstance().insert(OnlineCourseModel);
        if (result > 0) {
            onlineCourseList.add(OnlineCourseModel);
            return result;
        }
        return -1;
    }

    public int updateModel(OnlineCourseModel OnlineCourseModel) {
        int result = OnlineCourseDAL.getInstance().update(OnlineCourseModel);
        if (result > 0) {
            int index = onlineCourseList.indexOf(OnlineCourseModel);
            if (index != -1) {
                onlineCourseList.set(index, OnlineCourseModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        OnlineCourseModel OnlineCourseModel = getModelById(id);

        int result = OnlineCourseDAL.getInstance().delete(id);
        if (result > 0) {
            onlineCourseList.remove(OnlineCourseModel);
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        return result;
    }

    public List<OnlineCourseModel> searchModel(String value, String[] columns) {
        List<OnlineCourseModel> items = getAllModels();
        List<OnlineCourseModel> results = new ArrayList<>();
        for (OnlineCourseModel item : items) {
            if (checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(OnlineCourseModel OnlineCourseModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "CourseID":
                    if (Integer.parseInt(value) == OnlineCourseModel.getId()) {
                        return true;
                    }
                    break;
                case "url":
                    if (OnlineCourseModel.getUrl().toLowerCase().contains(value.toLowerCase())) {
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