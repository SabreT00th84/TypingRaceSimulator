package Part2.Models;

import javafx.beans.property.*;

import java.util.*;

/**
 * @author Eesa Adam
 * @version 2
 */
public class Typist {

    public static final Map<String, Double> typingStyles = new LinkedHashMap<>();

    static {
        typingStyles.put("Touch Typist (90% Accuracy, Higher chance of burnout)", 0.90);
        typingStyles.put("Hunt & Peck (70% Accuracy, Medium chance of burnout)", 0.70);
        typingStyles.put("Phone Thumbs (50% Accuracy, Lower chance of burnout)", 0.50);
        typingStyles.put("Voice-to-Text (30% Accuracy, Very low chance of burnout)", 0.30);
    }

    public static final Map<String, Double[]> keyboardTypes = new LinkedHashMap<>();

    static {
        keyboardTypes.put("Touchscreen (-5% Accuracy, 1 character per turn)", new Double[]{-0.10, 1.0});
        keyboardTypes.put("Membrane (+0% Accuracy, 1 character per turn)", new Double[]{0.00, 1.0});
        keyboardTypes.put("Mechanical (+5% Accuracy, 2 character per turn)", new Double[]{0.05, 2.0});
        keyboardTypes.put("Stenographic (+10% Accuracy, 3 character per turn)", new Double[]{0.10, 3.0});
    }

    public static final List<String> awards = new ArrayList<>();
    static {
        awards.add("Speed Demon");
        awards.add("Iron Fingers");
    }

    private String name;
    private String symbol;
    private String colour; //As a hex code
    private double accuracy;
    private int speed;
    private final List<RaceStat> raceStats;
    private final Set<String> awardsReceived;


    //Race Specific
    private final IntegerProperty progress;

    private final BooleanProperty burntOut;
    private final IntegerProperty burnoutRemaining;
    private int burnoutDurationModifier;

    private final IntegerProperty speedBoost;
    private int speedBoostRemaining;

    private final DoubleProperty accuracyBoost;
    private int accuracyBoostRemaining;
    private double accuracyModifer;

    private final BooleanProperty justMistyped;
    private double mistypeChanceModifier;

    private double timeTaken; // In s
    private int numberOfMistypes;
    private int numberOfBurnouts;



    // Constructor of class Typist

    /**
     * Constructor for objects of class Typist.
     * Creates a new typist with a given symbol, name, and accuracy rating.
     *
     * @param typistName     the name of the typist (e.g. "TURBOFINGERS")
     * @param typistSymbol   a single Unicode character representing this typist (e.g. '①', '②', '③')
     * @param colour         the typist's colour, as a hex code
     * @param typistAccuracy the typist's accuracy rating, between 0.0 and 1.0
     * @param speed          the typist's speed modifier, between 1 and 10
     */
    public Typist(String typistName, String typistSymbol, String colour, double typistAccuracy, int speed) {
        this.name = typistName;
        this.symbol = typistSymbol;
        this.colour = colour;
        this.accuracy = typistAccuracy;
        this.speed = speed;
        this.raceStats = new LinkedList<>();
        this.awardsReceived = new HashSet<>();


        //Race Specific
        this.progress = new SimpleIntegerProperty(0);

        this.burntOut = new SimpleBooleanProperty(false);
        this.burnoutRemaining = new SimpleIntegerProperty(0);
        this.burnoutDurationModifier = 0;

        this.speedBoost = new SimpleIntegerProperty(0);
        this.speedBoostRemaining = 0;

        this.accuracyBoost = new SimpleDoubleProperty(0);
        this.accuracyBoostRemaining = 0;
        this.accuracyModifer = 0;

        this.justMistyped = new SimpleBooleanProperty(false);
        this.mistypeChanceModifier = 0;

        this.timeTaken = 0;
    }


    // Methods of class Typist

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public double getAccuracy() {
        return Math.min (accuracy + accuracyBoost.get() + accuracyModifer, 1);
    }

    public double getBaseAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double newAccuracy) {
        if (newAccuracy < 0) {
            accuracy = 0;
        } else if (newAccuracy > 1) {
            accuracy = 1;
        } else {
            accuracy = newAccuracy;
        }
    }

    public int getSpeed() {
        return speed + speedBoost.get();
    }

    public void setSpeed(int newSpeed) {
        if (newSpeed < 1) {
            speed = 1;
        } else if (newSpeed > 10) {
            speed = 10;
        } else {
            speed = newSpeed;
        }
    }

    public List<RaceStat> getRaceStats() {
        return raceStats;
    }

    public RaceStat getLastRaceStat() {
        return raceStats.getLast();
    }

    public void addRaceStat(RaceStat raceStat) {
        raceStats.add(raceStat);
    }

    public Set<String> getAwardsReceived() {
        return awardsReceived;
    }

    public void addAward(String award) {
        awardsReceived.add(award);
    }

    public int getProgress() {
        return progress.get();
    }

    public IntegerProperty getProgressProperty() {
        return progress;
    }

    public void takeTurn(double mistypeBaseChance,
                         int slideBackAmount,
                         int burnoutDuration,
                         double baseAccuracyMultiplier) {

        if (hasJustMistyped()) {
            justMistyped.set(false);
        }

        if (isBurntOut()) {
            recoverFromBurnout();
        } else if (Math.random() < (1 - getAccuracy()) * (mistypeBaseChance + mistypeChanceModifier)) {
            slideBack(slideBackAmount);
        } else if (Math.random() < 0.05 * getAccuracy() * getAccuracy()) {
            burnOut(burnoutDuration);
            setAccuracy(accuracy - (accuracy * baseAccuracyMultiplier));
        } else {
            typeCharacter();
        }

        if (getSpeedBoostRemaining() > 0) {
            speedBoostRemaining--;
        }

        if (getSpeedBoostRemaining() <= 0) {
            speedBoostRemaining = 0;
            speedBoost.set(0);
        }

        accuracyBoostRemaining--;

        if (getAccuracyBoostRemaining() <= 0) {
            accuracyBoost.set(-accuracyBoost.get());
            accuracyBoostRemaining = Integer.MAX_VALUE;
        }

        timeTaken += 0.25;
    }

    private void typeCharacter() {
        progress.set(getProgress() + getSpeed());
    }

    private void slideBack(int amount) {

        justMistyped.set(true);
        numberOfMistypes++;

        if ((getProgress() - amount) < 0) {
            progress.set(0);
        } else {
            progress.set(getProgress() - amount);
        }
    }

    public boolean isBurntOut() {
        return burntOut.get();
    }

    public BooleanProperty getBurntOutProperty() {
        return burntOut;
    }

    private void burnOut(int turns) {
        burntOut.set(true);
        burnoutRemaining.set(turns + burnoutDurationModifier);
        numberOfBurnouts++;
    }

    private void recoverFromBurnout() {
        if (isBurntOut()) {
            burnoutRemaining.set(burnoutRemaining.get() - 1);

            if (burnoutRemaining.get() <= 0) {
                burntOut.set(false);
            }
        }
    }

    public int getBurnoutTurnsRemaining() {
        return burnoutRemaining.get();
    }

    public IntegerProperty getBurnoutRemainingProperty() {
        return burnoutRemaining;
    }

    public void setBurnoutDurationModifier(int burnoutDurationModifier) {
        this.burnoutDurationModifier = burnoutDurationModifier;
    }

    public IntegerProperty getSpeedBoostProperty() {
        return speedBoost;
    }

    public void setSpeedBoost(int speedBoost, int speedBoostDuration) {
        this.speedBoost.set(speedBoost);
        speedBoostRemaining = speedBoostDuration;
    }

    public int getSpeedBoostRemaining() {
        return speedBoostRemaining;
    }

    public DoubleProperty getAccuracyBoostProperty() {
        return accuracyBoost;
    }

    public void setAccuracyBoost(double accuracyBoost, int accuracyBoostDuration) {
        this.accuracyBoost.set(accuracyBoost);
        accuracyBoostRemaining = accuracyBoostDuration;
    }

    public int getAccuracyBoostRemaining() {
        return accuracyBoostRemaining;
    }

    public void setAccuracyModifier(double accuracyModifier) {
        this.accuracyModifer = accuracyModifier;
    }

    public boolean hasJustMistyped() {
        return justMistyped.get();
    }

    public BooleanProperty getJustMistypedProperty() {
        return justMistyped;
    }

    public void setMistypeChanceModifier(double mistypeChanceModifier) {
        this.mistypeChanceModifier = mistypeChanceModifier;
    }

    public double getMistypeChanceModifier() {
        return mistypeChanceModifier;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public int getNumberOfMistypes() {
        return numberOfMistypes;
    }

    public int getNumberOfBurnouts() {
        return numberOfBurnouts;
    }

    public void reset() {
        progress.set(0);
        burntOut.set(false);
        burnoutRemaining.set(0);
        burnoutDurationModifier = 0;
        speedBoost.set(0);
        speedBoostRemaining = 0;
        accuracyBoost.set(0);
        accuracyBoostRemaining = 0;
        justMistyped.set(false);
        timeTaken = 0;
    }

    public boolean isFinished(int passageLength) {
        return getProgress() >= passageLength;
    }
}