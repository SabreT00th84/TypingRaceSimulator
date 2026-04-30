package Part2.ViewModels;

import Part2.Models.Typist;

import java.util.Set;

public class EditTypistsViewModel {

    public Set<String> getStyles() {
        return Typist.typingStyles.keySet();
    }

    public Set<String> getKeyboards() {
        return Typist.keyboardTypes.keySet();
    }

    public String colourToHex(double red, double green, double blue) {
        return String.format("#%02X%02X%02X",
                (int) Math.round(red * 255),
                (int) Math.round(green * 255),
                (int) Math.round(blue * 255)
        );
    }
}
