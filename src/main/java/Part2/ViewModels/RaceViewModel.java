package Part2.ViewModels;

import Part2.Models.Typist;

public class RaceViewModel {

    private static final int BASE_SLIDE_BACK_AMOUNT = 2;

    private final int slideBackAmount;
    private final Typist[] typists;
    private final String passage;

    public RaceViewModel(Typist[] typists, boolean autocorrect, String passage) {
        this.typists = typists;
        this.slideBackAmount = autocorrect ? (BASE_SLIDE_BACK_AMOUNT / 2) : BASE_SLIDE_BACK_AMOUNT;
        this.passage = passage;
    }

    public Typist[] getTypists() {
        return typists;
    }

    public String getPassage() {
        return passage;
    }

    public void startRace () {
        for (int i = 0; i < 50; i++) {
            typists[0].typeCharacter();
        }
    }
}
