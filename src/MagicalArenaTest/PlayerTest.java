package MagicalArenaTest;

import MagicalArena.Player;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlayerTest {
    @Test
    public void RegisteringPlayersFromObjectTest() {
        Player player1 = new Player("Alice", 100,129,105);
        Player player2 = new Player("Bob", 98,72,123);

        try {
            player1.RegisterPlayerFromObject(player1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            player2.RegisterPlayerFromObject(player2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertNotEquals(player1.getPlayerId(), player2.getPlayerId());
    }

    @Test
    public void checkName() {
        Player player1 = new Player("Alice", 100,129,106);

        assertEquals("Alice",player1.getPlayerName() );
    }

    @Test
    public void checkHealth() {
        Player player1 = new Player("Alice", 100,129,106);

        assertEquals(100,player1.getHealth() );
    }

    @Test
    public void nameMisMatch() {
        Player player1 = new Player("Alice", 100,129,106);

        assertNotEquals(player1.getPlayerName(),1212 );
    }

    @Test
    public void HealthMismatch() {
        Player player1 = new Player("Alice", 100,129,106);

        assertNotEquals(player1.getHealth(),"Alice" );
    }



}
