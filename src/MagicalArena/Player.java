package MagicalArena;



import java.sql.SQLException;
import java.util.*;

public class Player {
    private int playerId;
    private String playerName;
    private int health;
    private int strength;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    private int attack;

    Player() {

    }

    public Player(String playerName, int health, int strength, int attack) {
        this.playerName = playerName;
        this.health = health;
        this.strength = strength;
        this.attack = attack;


    }

    void PlayerStats() {
        System.out.println("Player Name: " + getPlayerName());
        System.out.println("Player Health: " + getHealth());
    }
    public static boolean containsNumbers(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    void RegisterPlayer(Player p) {
        Scanner sc = new Scanner(System.in);


        System.out.println("Enter Player Name:");
        String playerName=sc.next();
        if(!playerName.isEmpty() && !containsNumbers(playerName)) {
            p.setPlayerName(playerName);
        }
        else{
                System.out.println("Please enter valid data");
                System.exit(-1);
        }
        try {

            System.out.println("Enter Player Attack Points:");

            p.setAttack(sc.nextInt());

            System.out.println("Enter Player Strength Points:");
            p.setStrength(sc.nextInt());

            System.out.println("Enter Player Health Points:");
            p.setHealth(sc.nextInt());
        }
        catch(InputMismatchException e){
            System.out.println(e);
            System.exit(-1);

        }


        try {
           setPlayerId(PlayerDbConnection.addPlayerToDb(p));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void RegisterPlayerFromObject(Player player) throws SQLException {

        setPlayerId(PlayerDbConnection.addPlayerToDb(player));

    }


}