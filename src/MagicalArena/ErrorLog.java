package MagicalArena;

import java.sql.*;

public class ErrorLog {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static void addError(String message) throws SQLException {


        String query=("INSERT INTO ErrorLog (ErrorType, timestamp) VALUES (?, NOW())");

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, message);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
}
