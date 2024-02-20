package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.OfficeAssignmentDAL;
import Model.OfficeAssignmentModel;

public class OfficeAssignmentBUS{
    private final List<OfficeAssignmentModel> officeAssignmentList = new ArrayList<>();
    private static OfficeAssignmentBUS instance;

    private OfficeAssignmentBUS() {
        this.officeAssignmentList.addAll(OfficeAssignmentDAL.getInstance().readDB());
    }

    public static OfficeAssignmentBUS getInstance() {
        if (instance == null) {
            instance = new OfficeAssignmentBUS();
        }
        return instance;
    }

    public void refresh() {
        officeAssignmentList.clear();
        officeAssignmentList.addAll(OfficeAssignmentDAL.getInstance().readDB());
    }

    public List<OfficeAssignmentModel> getAllModels() {
        return Collections.unmodifiableList(officeAssignmentList);
    }


    public OfficeAssignmentModel getModelById(int id) {
        refresh();
        for (OfficeAssignmentModel OfficeAssignmentModel : officeAssignmentList) {
            if (OfficeAssignmentModel.getInstructorID() == id) {
                return OfficeAssignmentModel;
            }
        }
        return null;
    }

    public int addModel(OfficeAssignmentModel OfficeAssignmentModel) {
        if(OfficeAssignmentModel.getInstructorID() <= 0
                || OfficeAssignmentModel.getLocation().isEmpty() || OfficeAssignmentModel.getLocation() == null
                || OfficeAssignmentModel.getTimestamp() == null || OfficeAssignmentModel.getTimestamp().getTime() <= System.currentTimeMillis()) {
            throw new IllegalArgumentException("error information, try again!!!");
        }

        int result = OfficeAssignmentDAL.getInstance().insert(OfficeAssignmentModel);
        if(result > 0) {
            officeAssignmentList.add(OfficeAssignmentModel);
            return result;
        }
        return -1;
    }

    public int updateModel(OfficeAssignmentModel OfficeAssignmentModel) {
        int result = OfficeAssignmentDAL.getInstance().update(OfficeAssignmentModel);
        if (result > 0) {
            int index = officeAssignmentList.indexOf(OfficeAssignmentModel);
            if (index != -1) {
                officeAssignmentList.set(index, OfficeAssignmentModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        OfficeAssignmentModel OfficeAssignmentModel = getModelById(id);

        int result = OfficeAssignmentDAL.getInstance().delete(id);
        if (result > 0) {
            officeAssignmentList.remove(OfficeAssignmentModel);
        }else {
            throw new IllegalArgumentException("Invalid id: "+id);
        }
        return result;
    }

    public List<OfficeAssignmentModel> searchModel(String value, String[] columns) {
        List<OfficeAssignmentModel> items = getAllModels();
        List<OfficeAssignmentModel> results = new ArrayList<>();
        for (OfficeAssignmentModel item : items) {
            if(checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(OfficeAssignmentModel OfficeAssignmentModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "instructorid":
                    if (Integer.parseInt(value) == OfficeAssignmentModel.getInstructorID()) {
                        return true;
                    }
                    break;
                case "location":
                    if (OfficeAssignmentModel.getLocation().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "timestamp":
                    if (value.equals(OfficeAssignmentModel.getTimestamp().toString())) {
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