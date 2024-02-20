package com.school.schoolmanagement.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface defining common database operations for entities.
 *
 * @param <T> the entity type that this DAO interface will handle
 */
public interface IDAO<T> {

  /**
   * Reads all entries from the database table associated with this DAO.
   *
   * @return an ArrayList of all Entity objects in the table
   * @throws SQLException on any exception arising from database access
   *                      errors
   */
  ArrayList<T> readDatabase() throws SQLException;

  /**
   * Inserts an entity to the database table associated with this DAO.
   *
   * @param e the entity to insert
   * @return the number of rows affected by the insert statement
   * @throws SQLException on any exception arising from database access
   *                      errors or non-unique primary key
   *                      constraints violated
   */
  int insert(T e) throws SQLException;

  /**
   * Updates an existing entry in the database table associated with this DAO.
   *
   * @param e the entity to update
   * @return the number of rows affected by the update statement
   * @throws SQLException on any exception arising from database access
   *                      errors or identical primary key
   *                      constraints violated
   * 
   */
  int update(T e) throws SQLException;

  /**
   * Deletes a given entity from the database table associated with this DAO.
   *
   * @param id the primary key of the entry to delete
   * @return the number of rows affected by the deletion statement
   * @throws SQLException on any exception arising from database access
   *                      errors
   * 
   */
  int delete(int id) throws SQLException;

  /**
   * Searches the database table associated with this DAO for entities that match
   * the given condition
   * in the specified columns.
   *
   * @param condition   the search condition to use
   * @param columnNames the names of the columns to search in
   * @return a list of entities that match the search condition
   * @throws SQLException if there is any error accessing the database
   */
  List<T> search(String condition, String[] columnNames) throws SQLException;
}