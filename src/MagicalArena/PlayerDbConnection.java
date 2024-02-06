package MagicalArena;

import java.sql.*;

public class PlayerDbConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    public static void createDb() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
// Create a statement to execute SQL queries.
        Statement statement = connection.createStatement();
// Create a table to store the player data.
        statement.execute("CREATE DATABASE IF NOT EXISTS GAME;");

        statement.execute("USE GAME;");
    }

    public static void createTables() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
// Create a statement to execute SQL queries.
        Statement statement = connection.createStatement();
// Create a table to store the player data.
        statement.execute("CREATE TABLE IF NOT EXISTS players (id INT NOT NULL AUTO_INCREMENT, playerName VARCHAR(255) NOT NULL, " +
                "attackPoints INT NOT NULL,strengthPoints INT NOT NULL,HealthPoints INT NOT NULL, PRIMARY KEY (id))");

        statement.execute("CREATE TABLE IF NOT EXISTS gameResult (resultId INT NOT NULL AUTO_INCREMENT, " +
                "winnerId INT NOT NULL,winnerName varchar(256),looserId INT NOT NULL, looserName varchar(256),timestamp TimeStamp," +
                "PRIMARY KEY (resultId));");
    }

    public static int addPlayerToDb(Player player) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO players (playerName, attackPoints, strengthPoints, healthPoints) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getAttack());
            statement.setInt(3, player.getStrength());
            statement.setInt(4, player.getHealth());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Adding player failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Adding player failed, no ID obtained.");
                }
            }
        }
    }
    Player getPlayerFromDb() throws SQLException{

        // Create a connection to the database.
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
// Create a statement to execute SQL queries.
        Statement statement = connection.createStatement();

        ResultSet resultSet=statement.executeQuery("SELECT * FROM players ORDER BY RAND() LIMIT 1;" );
        Player p=new Player();
        while (resultSet.next()) {
            // Get the data from the current row
            p.setPlayerId(resultSet.getInt("id"));
            p.setPlayerName(resultSet.getString("playerName"));
            p.setAttack(resultSet.getInt("attackPoints"));
            p.setStrength(resultSet.getInt("strengthPoints"));
            p.setHealth(resultSet.getInt("HealthPoints"));
        }
// Close the statement.
        statement.close();
// Close the connection.
        connection.close();

        return p;

    }
    int countRecordsInDb() throws SQLException, SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
// Create a statement to execute SQL queries.
        Statement statement = connection.createStatement();
        // Execute a query to count the records in the table
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM players");

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }

        // Print the number of records


        // Close the statement
        statement.close();

        // Close the connection
        connection.close();

        return 0;
    }

}