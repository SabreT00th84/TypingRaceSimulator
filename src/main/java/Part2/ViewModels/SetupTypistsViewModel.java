package Part2.ViewModels;

import Part2.Models.RaceConfig;
import Part2.Models.Typist;
import java.util.Set;

public class SetupTypistsViewModel {
    private final RaceConfig config;
    private final Typist[] typists;

    public SetupTypistsViewModel(RaceConfig config) {
        this.config = config;
        this.typists = new Typist[config.numOfTypists()];
    }

    public Set<String> getStyles() {
        return Typist.typingStyles.keySet();
    }

    public double getStyleAccuracy(String style) {
        return Typist.typingStyles.get(style);
    }

    public double getKeyboardAccuracy(String keyboard) {
        return Typist.keyboardTypes.get(keyboard)[0];
    }

    public int getKeyboardSpeed(String keyboard) {
        return Typist.keyboardTypes.get(keyboard)[1].intValue();
    }

    public Set<String> getKeyboards() {
        return Typist.keyboardTypes.keySet();
    }

    public RaceConfig getConfig() {
        return config;
    }

    public String addTypist(int index, String name, String symbol, String colour,
                          double accuracy, int speed, boolean wristSupport,
                          boolean energyDrink, boolean headphones)
    {
        if (validateTypist(name, symbol)) {
            double modifiedAccuracy = accuracy;

            if (config.night()) {
                modifiedAccuracy -= 0.10;
            }

            if (headphones) {
                modifiedAccuracy += 0.05;
            }

            Typist typist = new Typist(name, Character.toString(symbol.codePointAt(0)),
                    colour, modifiedAccuracy, speed);

            if (config.caffeine()) {
                typist.setSpeedBoost(1, 10);
            }

            if (wristSupport) {
                typist.setBurnoutDurationModifier(-1);
            }

            if (energyDrink) {
                typist.setAccuracyBoost(0.1, 15);
            }

            typists[index] = typist;

            return null;
        } else {
            return "Please enter a valid name and symbol for the typist.";
        }
    }

    public String colourToHex(double red, double green, double blue) {
        return String.format("#%02X%02X%02X",
                (int) Math.round(red * 255),
                (int) Math.round(green * 255),
                (int) Math.round(blue * 255)
        );
    }

    private boolean validateTypist(String name, String symbol) {
        return !(name == null || name.trim().isBlank() || symbol == null || symbol.trim().isBlank());
    }

    public String validateAllTypists() {
        for (Typist typist : typists) {
            if (typist == null) {
                return "Please add all typists.";
            }
        }
        return null;
    }

    public Typist[] getTypists() {
        return typists;
    }
}
