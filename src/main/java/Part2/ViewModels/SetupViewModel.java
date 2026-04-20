package Part2.ViewModels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.LinkedHashMap;

public class SetupViewModel {
    public ObservableMap<String, String> passageStrings = FXCollections.observableMap(new LinkedHashMap<>());
    {
        passageStrings.put("Short", "The quick brown fox jumps over the lazy dog");
        passageStrings.put("Medium", "I must not fear. Fear is the mind-killer. Fear is the little-death that brings total obliteration. I will face my fear. I will permit it to pass over me and through me. And when it has gone past I will turn the inner eye to see its path. Where the fear has gone there will be nothing. Only I will remain.");
        passageStrings.put("Long", "And we shouldn't be here at all, if we'd known more about it before we started. But I suppose it's often that way. The brave things in the old tales and songs, Mr. Frodo: adventures, as I used to call them. I used to think that they were things the wonderful folk of the stories went out and looked for, because they wanted them, because they were exciting and life was a bit dull, a kind of a sport, as you might say. But that's not the way, as you put it. But I expect they had lots of chances, like us, of turning back, only they didn't. And if they had, we shouldn't know, because they'd have been forgotten. We hear about those as just went on - and not all to a good end, mind you; at least not to what folk inside a story and not outside it call a good end. You know, coming home, and finding things all right, though not quite the same - like old Mr. Bilbo. But those aren't always the best tales to hear, though they may be the best tales to get landed in! I wonder what sort of a tale we've fallen into?");
    }

    public StringProperty selected = new SimpleStringProperty("Short");
    public StringProperty text = new SimpleStringProperty("");

    public boolean addPassage(String text) {
        if (!(selected.get() == null || selected.get().isBlank() || text == null || text.isBlank())) {
            passageStrings.put(selected.get(), text);
            return true;
        } else {
            return false;
        }
    }
}