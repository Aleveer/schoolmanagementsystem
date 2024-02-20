package com.school.schoolmanagement.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.dal.OnsiteCourseDAL;
import com.school.schoolmanagement.models.OnsiteCourseModel;

public class OnsiteCourseBUS {
    private final List<OnsiteCourseModel> onsiteCourseList = new ArrayList<>();
    private static OnsiteCourseBUS instance;

    private OnsiteCourseBUS() {
        this.onsiteCourseList.addAll(OnsiteCourseDAL.getInstance().readDB());
    }

    public static OnsiteCourseBUS getInstance() {
        if (instance == null) {
            instance = new OnsiteCourseBUS();
        }
        return instance;
    }

    public void refresh() {
        onsiteCourseList.clear();
        onsiteCourseList.addAll(OnsiteCourseDAL.getInstance().readDB());
    }

    public List<OnsiteCourseModel> getAllModels() {
        return Collections.unmodifiableList(onsiteCourseList);
    }

    public OnsiteCourseModel getModelById(int id) {
        refresh();
        for (OnsiteCourseModel OnsiteCourseModel : onsiteCourseList) {
            if (OnsiteCourseModel.getId() == id) {
                return OnsiteCourseModel;
            }
        }
        return null;
    }

    public int addModel(OnsiteCourseModel OnsiteCourseModel) {
        if (OnsiteCourseModel.getLocation().isEmpty() || OnsiteCourseModel.getLocation() == null
                || OnsiteCourseModel.getDays().isEmpty() || OnsiteCourseModel.getDays() == null
                || OnsiteCourseModel.getTime() == null) {
            throw new IllegalArgumentException("error information, try again!!!");
        }

        int result = OnsiteCourseDAL.getInstance().insert(OnsiteCourseModel);
        if (result > 0) {
            onsiteCourseList.add(OnsiteCourseModel);
            return result;
        }
        return -1;
    }

    public int updateModel(OnsiteCourseModel OnsiteCourseModel) {
        int result = OnsiteCourseDAL.getInstance().update(OnsiteCourseModel);
        if (result > 0) {
            int index = onsiteCourseList.indexOf(OnsiteCourseModel);
            if (index != -1) {
                onsiteCourseList.set(index, OnsiteCourseModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        OnsiteCourseModel OnsiteCourseModel = getModelById(id);

        int result = OnsiteCourseDAL.getInstance().delete(id);
        if (result > 0) {
            onsiteCourseList.remove(OnsiteCourseModel);
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        return result;
    }

    public List<OnsiteCourseModel> searchModel(String value, String[] columns) {
        List<OnsiteCourseModel> items = getAllModels();
        List<OnsiteCourseModel> results = new ArrayList<>();
        for (OnsiteCourseModel item : items) {
            if (checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(OnsiteCourseModel OnsiteCourseModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "CourseID":
                    if (Integer.parseInt(value) == OnsiteCourseModel.getId()) {
                        return true;
                    }
                    break;
                case "Location":
                    if (OnsiteCourseModel.getLocation().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "Days":
                    if (OnsiteCourseModel.getDays().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "Time":
                    if (value.equals(OnsiteCourseModel.getTime().toString())) {
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