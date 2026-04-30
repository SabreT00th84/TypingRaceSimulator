package Part2.ViewModels;

import Part2.AppState;
import Part2.Models.Typist;
import java.util.Set;

public class AddTypistsViewModel {
    private final AppState appState;
    private final int numOfTypists;
    private final int originalNumOfTypists;

    public AddTypistsViewModel(AppState appState, int numOfTypists) {
        this.appState = appState;
        this.numOfTypists = numOfTypists;
        this.originalNumOfTypists = appState.getTypists().size();
    }

    public Set<String> getStyles() {
        return Typist.typingStyles.keySet();
    }

    public Set<String> getKeyboards() {
        return Typist.keyboardTypes.keySet();
    }

    public int getNumOfTypists() {
        return numOfTypists;
    }

    public String addTypist(String name, String symbol, String colour,
                            String style, String keyboard, boolean wristSupport,
                            boolean energyDrink, boolean headphones
    )
    {
        if (!validateTypist(name, symbol)) {
            return "Please enter a valid name and symbol for the typist.";
        } else if (appState.getTypists().stream().anyMatch(t -> t.getName().equals(name))) {
            return "A typist with that name already exists.";
        } else {
            Typist typist = new Typist (
                    name,
                    Character.toString(symbol.codePointAt(0)),
                    colour
            );

            typist.setTypingStyle(style);
            typist.setKeyboardType(keyboard);

            typist.setWristSupport(wristSupport);
            typist.setEnergyDrink(energyDrink);
            typist.setHeadphones(headphones);

            appState.addTypist(typist);

            return null;
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
        return !(name == null || name.isBlank() || symbol == null || symbol.isBlank());
    }

    public String validateAllTypists() {
        if (appState.getTypists().size() == numOfTypists + originalNumOfTypists) {
            return null;
        } else {
            return "Please add all " + numOfTypists + " typists.";
        }
    }
}
