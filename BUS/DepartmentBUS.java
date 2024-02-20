package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.DepartmentDAL;
import Model.DepartmentModel;

public class DepartmentBUS{
    private final List<DepartmentModel> departmentList = new ArrayList<>();
    private static DepartmentBUS instance;

    private DepartmentBUS() {
        this.departmentList.addAll(DepartmentDAL.getInstance().readDB());
    }

    public static DepartmentBUS getInstance() {
        if (instance == null) {
            instance = new DepartmentBUS();
        }
        return instance;
    }

    public void refresh() {
        departmentList.clear();
        departmentList.addAll(DepartmentDAL.getInstance().readDB());
    }

    public List<DepartmentModel> getAllModels() {
        return Collections.unmodifiableList(departmentList);
    }


    public DepartmentModel getModelById(int id) {
        refresh();
        for (DepartmentModel DepartmentModel : departmentList) {
            if (DepartmentModel.getDepartmentID() == id) {
                return DepartmentModel;
            }
        }
        return null;
    }

    public int addModel(DepartmentModel DepartmentModel) {
        if(DepartmentModel.getName().isEmpty() || DepartmentModel.getName() == null
                || DepartmentModel.getBudget() <= 0
                || DepartmentModel.getAdministrator() <= 0
                || DepartmentModel.getStartDate() == null || DepartmentModel.getStartDate().getTime() <= System.currentTimeMillis()) {
            throw new IllegalArgumentException("error infomation, try again!!!");
        }

        int result = DepartmentDAL.getInstance().insert(DepartmentModel);
        if(result > 0) {
            departmentList.add(DepartmentModel);
            return result;
        }
        return -1;
    }

    public int updateModel(DepartmentModel DepartmentModel) {
        int result = DepartmentDAL.getInstance().update(DepartmentModel);
        if (result > 0) {
            int index = departmentList.indexOf(DepartmentModel);
            if (index != -1) {
                departmentList.set(index, DepartmentModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        DepartmentModel DepartmentModel = getModelById(id);

        int result = DepartmentDAL.getInstance().delete(id);
        if (result > 0) {
            departmentList.remove(DepartmentModel);
        }else {
            throw new IllegalArgumentException("Invalid id: "+id);
        }
        return result;
    }

    public List<DepartmentModel> searchModel(String value, String[] columns) {
        List<DepartmentModel> items = getAllModels();
        List<DepartmentModel> results = new ArrayList<>();
        for (DepartmentModel item : items) {
            if(checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(DepartmentModel DepartmentModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "departmentid":
                    if (Integer.parseInt(value) == DepartmentModel.getDepartmentID()) {
                        return true;
                    }
                    break;
                case "name":
                    if (DepartmentModel.getName().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "budget":
                    if (Double.parseDouble(value) == DepartmentModel.getBudget()) {
                        return true;
                    }
                    break;
                case "startdate":
                    if (value.equals(DepartmentModel.getStartDate().toString())) {
                        return true;
                    }
                    break;
                case "administrator":
                    if (Integer.parseInt(value) == DepartmentModel.getAdministrator()) {
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