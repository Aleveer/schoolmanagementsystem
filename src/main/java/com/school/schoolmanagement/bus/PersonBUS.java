package com.school.schoolmanagement.bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.school.schoolmanagement.dal.PersonDAL;
import com.school.schoolmanagement.models.PersonModel;

public class PersonBUS {
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

    //TODO: What's this function for?
    public int getMax() {
        refresh();
        int count = 0;
        for (PersonModel person : personList) {
            count++;
        }
        return count + 1;
    }

    public int addModel(PersonModel PersonModel) {
        if (PersonModel.getLastName().isEmpty() || PersonModel.getLastName() == null
                || PersonModel.getFirstName().isEmpty() || PersonModel.getFirstName() == null) {
            throw new IllegalArgumentException("Error information, try again!");
        }

        int result = PersonDAL.getInstance().insert(PersonModel);
        if (result > 0) {
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
        } else {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
        return result;
    }

    public List<PersonModel> searchModel(String value, String[] columns) {
        List<PersonModel> items = getAllModels();
        List<PersonModel> results = new ArrayList<>();
        for (PersonModel item : items) {
            if (checkFilter(item, value, columns)) {
                results.add(item);
            }
        }
        return results;

    }

    private boolean checkFilter(PersonModel personModel, String value, String[] columns) {
        for (String column : columns) {
            switch (column) {
                case "LastName":
                    if (personModel.getLastName().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "FirstName":
                    if (personModel.getFirstName().toLowerCase().contains(value.toLowerCase())) {
                        return true;
                    }
                    break;
                case "PersonID":
                    try {
                        int intValue = Integer.parseInt(value);
                        if (intValue == personModel.getPersonID()) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore if value cannot be parsed to integer
                    }
                    break;
                case "HireDate":
                    if (value.equals(String.valueOf(personModel.getHireDate()))) {
                        return true;
                    }
                    break;
                case "EnrollmentDate":
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String enrollmentDateAsString = (personModel.getEnrollmentDate() != null)
                            ? dateFormat.format(personModel.getEnrollmentDate())
                            : "";
                    if (value.equals(enrollmentDateAsString)) {
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