package MagicalArena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;

public class GameResultsDb {
    // Create a connection to the database.
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    void storeResults(Player winner,Player looser) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
// Create a statement to execute SQL queries.
            Statement statement = connection.createStatement();
// Create a table to store the player data.

            //insert into gameResult(WinnerId,winnerName,looserId,looserName,timestamp) values(1,"Ayush",2,"Rakesh",NOW());
            statement.execute("insert into gameResult(WinnerId,winnerName,looserId,looserName,timestamp) values(" + winner.getPlayerId() + ",\"" + winner.getPlayerName() + "\"," + looser.getPlayerId() + ",\"" + looser.getPlayerName() + "\",NOW());");


// Close the statement.
            statement.close();
// Close the connection.
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
