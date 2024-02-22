package main.java.com.school.schoolmanagement.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnect {

    private Connection connection = null;
    private static ResourceBundle rb = ResourceBundle.getBundle(
            "config.database");
    private static String driver = rb.getString("driver");
    private static String url = rb.getString("url");
    private static String user = rb.getString("user");
    private static String password = rb.getString("password");
    private static DatabaseConnect instance;

    private DatabaseConnect() {
    }

    public static DatabaseConnect getInstance() {
        if (instance == null) {
            instance = new DatabaseConnect();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement getPreparedStatement(
            String sql,
            Object... args) throws SQLException {
        try {
            PreparedStatement preparedStatement = getInstance()
                    .getConnection()
                    .prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new SQLException("Error: " + e.getMessage() + " with sql: " + sql);
        }
    }

    /**
     * Execute query
     *
     * @param sql
     * @param args
     * @return ResultSet after executing query
     * @throws SQLException
     */
    public static ResultSet executeQuery(String sql, Object... args)
            throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeQuery();
    }

    /**
     * Execute update
     *
     * @param sql
     * @param args
     * @return number of rows affected by the update
     * @throws SQLException
     */
    public static int executeUpdate(String sql, Object... args)
            throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeUpdate();
    }

    /**
     * Close connection
     *
     * @throws SQLException
     */
    public static void closeConnection() {
        try {
            if (getInstance().connection != null && !getInstance().connection.isClosed()) {
                getInstance().connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkConnection() {
        getConnection();
        try {
            return getConnection() != null && !getConnection().isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void beginTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void endTransaction() {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollbackTransaction() {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}