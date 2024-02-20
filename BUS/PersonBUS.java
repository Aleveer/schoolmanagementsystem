package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.PersonDAL;
import Model.PersonModel;

public class PersonBUS{
    private final List<PersonModel> personList = new ArrayList<>();
    private static PersonBUS instance;

    private PersonBUS() {
        this.personList.addAll(PersonDAL.getInstance().readDB());
    }

    public static PersonBUS getInstance() {
        if (instance == null) {
            instance = new PersonBUS();
        }
        return instance;
    }

    public void refresh() {
        personList.clear();
        personList.addAll(PersonDAL.getInstance().readDB());
    }

    public List<PersonModel> getAllModels() {
        return Collections.unmodifiableList(personList);
    }


    public PersonModel getModelById(int id) {
        refresh();
        for (PersonModel PersonModel : personList) {
            if (PersonModel.getPersonID() == id) {
                return PersonModel;
            }
        }
        return null;
    }

    public int addModel(PersonModel PersonModel) {
        if(PersonModel.getLastName().isEmpty() || PersonModel.getLastName() == null
                || PersonModel.getFirstName().isEmpty() || PersonModel.getFirstName() == null
        ) {
            throw new IllegalArgumentException("error infomation, try again!!!");
        }

        int result = PersonDAL.getInstance().insert(PersonModel);
        if(result > 0) {
            personList.add(PersonModel);
            return result;
        }
        return -1;
    }

    public int updateModel(PersonModel PersonModel) {
        int result = PersonDAL.getInstance().update(PersonModel);
        if (result > 0) {
            int index = personList.indexOf(PersonModel);
            if (index != -1) {
                personList.set(index, PersonModel);
            }
        }
        return result;
    }

    public int deleteModel(int id) {
        PersonModel PersonModel = getModelById(id);

        int result = PersonDAL.getInstance().delete(id);
        if (result > 0) {
            personList.remove(PersonModel);
        }else {
            throw new IllegalArgumentException("Invalid id: "+id);
        }
        return result;
    }

    public List<PersonModel> searchModel(String value, String[] columns) {
        List<PersonModel> items = getAllModels();
        List<PersonModel> results = new ArrayList<>();
        for (PersonModel item : items) {
            if(checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(PersonModel PersonModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column.toLowerCase()) {
                case "personid":
                    if (Integer.parseInt(value) == PersonModel.getPersonID()) {
                        return true;
                    }
                    break;
                case "lastname":
                    if (PersonModel.getLastName().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "firstname":
                    if (PersonModel.getFirstName().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "hiredate":
                    if (value.equals(PersonModel.getHireDate().toString())) {
                        return true;
                    }
                    break;
                case "enrollmentdate":
                    if (value.equals(PersonModel.getEnrollmentDate().toString())) {
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