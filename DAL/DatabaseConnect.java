package DAL;

import java.sql.*;

public class DatabaseConnect {
    private Connection connection = null;
    private static String url = "jdbc:mysql://localhost:3306/school";
    private static String user = "root";
    private static String password = "";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    private static DatabaseConnect instance;

    private DatabaseConnect() {}

    private static DatabaseConnect getInstance() {
        if(instance == null) {
            instance = new DatabaseConnect();
        }
        return instance;
    }

    private Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            }
            System.out.println(connection);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Include it in your project's classpath.");
        }
        return null;
    }

    public static PreparedStatement getPreparedStatement(String sql, Object... args) throws SQLException {
        try {
            PreparedStatement preparedStatement = getInstance().getConnection().prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            return preparedStatement;
        } catch (SQLException e) {
            throw new SQLException("Error: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql, Object... args)
            throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeQuery();
    }

    public static int executeUpdate(String sql, Object... args)
            throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(sql, args);
        return preparedStatement.executeUpdate();
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

    public static void main(String[] args) {
        if (getInstance().checkConnection()) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
