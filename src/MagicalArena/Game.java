package MagicalArena;

import java.sql.SQLException;
import java.util.*;


public class Game {

    private Player winner;
    private Player looser;
    private Player player1;
    private Player player2;
    private static final int ADD_PLAYER_OPTION = 1;
    private static final int PLAY_GAME_OPTION = 2;
    private static final int EXIT_OPTION = 3;

    private boolean continueGame = true;


    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getLooser() {
        return looser;
    }

    public void setLooser(Player looser) {
        this.looser = looser;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    int PlayersInDb(){
        PlayerDbConnection connected=new PlayerDbConnection();
        int playersInDb=-1;
        Scanner sc = new Scanner(System.in);
        try {
            playersInDb=connected.countRecordsInDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playersInDb;
    }
    Player getRandomPlayerFromDb(){
        PlayerDbConnection p=new PlayerDbConnection();
        try {
            return p.getPlayerFromDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void storeGameResults(Player winner,Player looser){
        GameResultsDb grb=new GameResultsDb();
        try {

            grb.storeResults(winner,looser);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void welcomeScreen() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome To Magic Arena");
        System.out.println("Choose From the options");

        try {
            PlayerDbConnection.createDb();
            PlayerDbConnection.createTables();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (PlayersInDb() < 2) {
            System.out.println(ADD_PLAYER_OPTION + ". Add New player");
            System.out.println(EXIT_OPTION + ". Exit Game");
        } else {
            System.out.println(ADD_PLAYER_OPTION + ". Add New player");
            System.out.println(PLAY_GAME_OPTION + ". Play Game");
            System.out.println(EXIT_OPTION + ". Exit Game");
        }

        int option = sc.nextInt();
        handleUserChoice(option);
    }

    private void handleUserChoice(int option) {
        Scanner sc=new Scanner(System.in);
        switch (option) {
            case ADD_PLAYER_OPTION:
                Player p = new Player();
                p.RegisterPlayer(p);
                System.out.println("Player registered Successfully");
                break;

            case PLAY_GAME_OPTION:
                if (PlayersInDb() >= 2) {
                    matchPlayers();
                    startGame();
                    System.out.println("Start new Game: yes/no");
                    String choice = sc.next();

                    if (choice.equalsIgnoreCase("no")) {
                        System.out.println("Game Completed");
                        continueGame = false;
                    } else if (!choice.equalsIgnoreCase("yes")) {
                        System.out.println("Please Choose a Valid Option");
                    }
                } else {
                    System.out.println("Not enough players to start the game.");
                }
                break;

            case EXIT_OPTION:
                System.out.println("Game Completed");
                continueGame = false;
                break;

            default:
                System.out.println("Please Choose a Valid Option");
        }

        if (continueGame) {
            welcomeScreen();
        }
    }

    void matchPlayers(){

        player1= getRandomPlayerFromDb();
        player2= getRandomPlayerFromDb();

        while(player1.getPlayerId() == player2.getPlayerId()){
            if(player1.getPlayerId()==player2.getPlayerId()){
                player2= getRandomPlayerFromDb();

            }
            else break;
        }
    }



    void startGame() {
        Player attacker;
        Player defender;

        Dice attackDice = new Dice(Dice.DiceType.ATTACK, 6);
        Dice defenceDice = new Dice(Dice.DiceType.DEFENCE, 6);

        if (player1.getHealth() <= player2.getHealth()) {
            attacker = player1;
            defender = player2;
        } else {
            attacker = player2;
            defender = player1;
        }
        int round = 1;
        while (true) {

            System.out.println("Round: " + round);
            round++;

            int attackDieValue = attackDice.roll();
            System.out.println("Player:" + attacker.getPlayerName() + " rolled " + attackDieValue + " on attack Die");
            int attackDamage = attackDieValue * attacker.getAttack();
            int defenceDieValue = defenceDice.roll();
            System.out.println("Player:" + defender.getPlayerName() + " rolled " + defenceDieValue + " on defence Die");

            int defenceDamage = defenceDieValue * defender.getStrength();

            int HealthLoss = attackDamage - defenceDamage;

            if (HealthLoss < 0) HealthLoss = 0; // Defended Successfully

            defender.setHealth(defender.getHealth() - HealthLoss);

            player2.PlayerStats();
            player1.PlayerStats();

            if (player1.getHealth() <= 0) {
                System.out.println(player2.getPlayerName() + " is the winner!");
                winner=player2;
                looser=player1;
                break;
            }

            if (player2.getHealth() <= 0) {
                System.out.println(player1.getPlayerName() + " is the winner!");
                winner=player1;
                looser=player2;
                break;
            }

            if (attacker.equals(player1)) {
                attacker = player2;
                defender = player1;
            } else {
                attacker = player1;
                defender = player2;
            }

            System.out.println();
        }
        storeGameResults(winner,looser);


    }


    public Game(){
    }
}