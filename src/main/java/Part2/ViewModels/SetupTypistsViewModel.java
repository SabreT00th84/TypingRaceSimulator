package Part2.ViewModels;

import Part2.Models.RaceConfig;
import Part2.Models.Typist;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SetupTypistsViewModel {
    private final Map<String, Double> styles = new LinkedHashMap<>();
    {
        styles.put("Touch Typist (90% Accuracy, Higher chance of burnout)", 0.90);
        styles.put("Hunt & Peck (70% Accuracy, Medium chance of burnout)", 0.70);
        styles.put("Phone Thumbs (50% Accuracy, Lower chance of burnout)", 0.50);
        styles.put("Voice-to-Text (30% Accuracy, Very low chance of burnout)", 0.30);
    }
    private final Map<String, Double[]> keyboards = new LinkedHashMap<>();
    {
        keyboards.put("Touchscreen (-5% Accuracy, 1 character per turn)", new Double[] {-0.10, 1.0});
        keyboards.put("Membrane (+0% Accuracy, 1 character per turn)", new Double[] {0.00, 1.0});
        keyboards.put("Mechanical (+5% Accuracy, 2 character per turn)", new Double[] {0.05, 2.0});
        keyboards.put("Stenographic (+10% Accuracy, 3 character per turn)", new Double[] {0.10, 3.0});
    }
    private final RaceConfig config;
    private final Typist[] typists;

    public SetupTypistsViewModel(RaceConfig config) {
        this.config = config;
        this.typists = new Typist[config.numOfTypists()];
    }

    public Set<String> getStyles() {
        return styles.keySet();
    }

    public double getStyleAccuracy(String style) {
        return styles.get(style);
    }

    public double getKeyboardAccuracy(String keyboard) {
        return keyboards.get(keyboard)[0];
    }

    public int getKeyboardSpeed(String keyboard) {
        return keyboards.get(keyboard)[1].intValue();
    }

    public Set<String> getKeyboards() {
        return keyboards.keySet();
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
