package Part2;

import Part2.Models.Typist;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.List;

public class AppState {
    private final ListProperty<Typist> typists;

    AppState() {
        this.typists = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public List<Typist> getTypists() {
        return typists.get();
    }

    public Typist getTypist(int index) {
        return typists.get(index);
    }

    public ListProperty<Typist> typistsProperty() {
        return typists;
    }

    public void addTypist(Typist typist) {
        typists.add(typist);
    }
}
