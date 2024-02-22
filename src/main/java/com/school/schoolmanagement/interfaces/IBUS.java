package src.main.java.com.school.schoolmanagement.interfaces;

import java.util.List;

public interface IBUS<T> {
  /**
   * Returns a list of all models.
   *
   * @return a list of all models
   */
  List<T> getAllModels();

  /**
   * Refreshes the data in the model list.
   */
  void refreshData();

  /**
   * Returns the model with the given id.
   *
   * @param id the id of the model to retrieve
   * @return the model with the given id, or null if not found
   */
  T getModelById(int id);

  /**
   * Adds the given model to the database.
   *
   * @param model the model to add
   * @return the number of rows affected by the operation
   */
  int addModel(T model);

  /**
   * Updates the given model in the database.
   *
   * @param model the model to update
   * @return the number of rows affected by the operation
   */
  int updateModel(T model);

  /**
   * Deletes the model with the given id from the database.
   *
   * @param id the id of the model to delete
   * @return the number of rows affected by the operation
   */
  int deleteModel(int id);

  /**
   * Searches for models that match the given value in the specified columns.
   *
   * @param value   the value to search for
   * @param columns the columns to search in
   * @return a list of models that match the search criteria
   */
  List<T> searchModel(String value, String[] columns);
}