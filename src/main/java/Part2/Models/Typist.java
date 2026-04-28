package Part2.Models;

import javafx.beans.property.*;

/**
 * @author Eesa Adam
 * @version 2
 */
public class Typist extends Part1.Typist {

    private final String emojiSymbol;
    private final String colour; //As a hex code
    private final int speed;
    private final IntegerProperty speedBoost;
    private int speedBoostDuration;
    private int burnoutDurationModifier;
    private final DoubleProperty accuracyBoost;
    private double accuracyBoostDuration;
    private double timeTaken; // In s
    private final IntegerProperty progress;
    private final BooleanProperty justMistyped;
    private final BooleanProperty burntOut;
    private final IntegerProperty burnoutRemaining;

    // Constructor of class Typist
    /**
     * Constructor for objects of class Typist.
     * Creates a new typist with a given symbol, name, and accuracy rating.
     *
     * @param typistName    the name of the typist (e.g. "TURBOFINGERS")
     * @param typistSymbol  a single Unicode character representing this typist (e.g. '①', '②', '③')
     * @param colour the typist's colour, as a hex code
     * @param typistAccuracy the typist's accuracy rating, between 0.0 and 1.0
     * @param speed the typist's speed modifier, between 1 and 10
     */
    public Typist(String typistName, String typistSymbol, String colour, double typistAccuracy, int speed)
    {
        super(typistSymbol.charAt(0), typistName, typistAccuracy);
        this.emojiSymbol = typistSymbol;
        this.colour = colour;
        this.speed = speed;
        this.speedBoost = new SimpleIntegerProperty(0);
        this.speedBoostDuration = 0;
        this.timeTaken = 0;
        this.progress = new SimpleIntegerProperty(super.getProgress());
        this.accuracyBoost = new SimpleDoubleProperty(super.getAccuracy());
        this.accuracyBoostDuration = 0;
        this.justMistyped = new SimpleBooleanProperty(super.hasJustMistyped());
        this.burntOut = new SimpleBooleanProperty(super.isBurntOut());
        this.burnoutRemaining = new SimpleIntegerProperty(super.getBurnoutTurnsRemaining());
    }


    // Methods of class Typist

    public String getEmojiSymbol() {
        return emojiSymbol;
    }

    public IntegerProperty getProgressProperty() {
        return progress;
    }

    @Override
    public void burnOut(int turns) {
        super.burnOut(turns);
        burnoutRemaining.set(super.getBurnoutTurnsRemaining());
        burntOut.set(super.isBurntOut());
    }

    @Override
    public void recoverFromBurnout() {
        super.recoverFromBurnout();
        burnoutRemaining.set(super.getBurnoutTurnsRemaining());
        burntOut.set(super.isBurntOut());
    }

    @Override
    public boolean isBurntOut() {
        return burntOut.get();
    }

    @Override
    public int getProgress() {
        return progress.get();
    }

    /**
     * Resets the typist to their initial state, ready for a new race.
     * Progress returns to zero, burnout is cleared entirely.
     */
    @Override
    public void resetToStart()
    {
        super.resetToStart();
        timeTaken = 0;
        progress.set(super.getProgress());
        burnoutRemaining.set(super.getBurnoutTurnsRemaining());
        burntOut.set(super.isBurntOut());
        justMistyped.set(super.hasJustMistyped());
    }

    /**
     * Advances the typist forward by a few characters along the passage.
     * Should only be called when the typist is not burnt out.
     * The typist is moved forward by as many characters as the speed modifier.
     * A speed boost is applied if the speed boost duration is more than zero.
     * The speed boost is removed after the duration has elapsed.
     * The whole method simulates 0.2s of typing time, so if
     * the speed is higher, the typist will type more characters
     * in 0.2s leading to a higher WPM.
     */
    @Override
    public void typeCharacter()
    {
        for (int i = 1; i <= getSpeed(); i++) {
            super.typeCharacter();
        }

        if (speedBoostDuration > 0) {
            speedBoostDuration--;
        }

        if (speedBoostDuration <= 0) {
            speedBoost.set(0);
        }

        accuracyBoostDuration--;

        if (accuracyBoostDuration < 0) {
            accuracyBoost.set(-accuracyBoost.get());
        }

        timeTaken += 0.2;
        progress.set(super.getProgress());
    }

    @Override
    public void slideBack(int amount) {
        super.slideBack(amount);
        progress.set(super.getProgress());
        justMistyped.set(super.hasJustMistyped());
    }

    @Override
    public double getAccuracy() {
        return super.getAccuracy() + accuracyBoost.get();
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public String getColour() {
        return colour;
    }

    public void setSpeedBoost(int speedBoost, int speedBoostDuration) {
        this.speedBoost.set(speedBoost);
        this.speedBoostDuration = speedBoostDuration - 1;
    }

    public IntegerProperty getSpeedBoostProperty() {
        return speedBoost;
    }

    public int getSpeed() {
        return speed + speedBoost.get();
    }

    public void setAccuracyBoost(double accuracyBoost, int accuracyBoostDuration) {
        this.accuracyBoost.set(accuracyBoost);
        this.accuracyBoostDuration = accuracyBoostDuration;
    }

    public DoubleProperty getAccuracyBoostProperty() {
        return accuracyBoost;
    }

    public void setBurnoutDurationModifier(int burnoutDurationModifier) {
        this.burnoutDurationModifier = burnoutDurationModifier;
    }
}
