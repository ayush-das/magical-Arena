package MagicalArena;

import java.util.Random;

public class Dice {
    private final Random random = new Random();
    private final int numberOfSides;
    public enum DiceType {
        ATTACK,
        DEFENCE
    }

    public Dice(DiceType diceType, int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public int roll() {
        // create here a random number belonging to range 1-numberOfSides
        return random.nextInt(this.numberOfSides) + 1;

    }
}
