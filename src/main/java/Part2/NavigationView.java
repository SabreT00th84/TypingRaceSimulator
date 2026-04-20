package Part2;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class NavigationView extends View {
    private ObjectProperty<View> currentView;

    protected NavigationView(Object... o) {
        super(o);
    }

    /**
     * Initialises the currentView Property. Subclasses' overriding
     * this method must call super.init() on the first line.
     *
     * @param o 0 or more parameters passed in from the constructor.
     */
    @Override
    protected void init(Object... o) {
        this.currentView = new SimpleObjectProperty<>(this);
    }

    /**
     * Switches the currentView to the newView provided.
     *
     * @param newView the view to navigate to.
     */
    protected void switchView(View newView) {
        currentView.set(newView);
    }

    /**
     * Getter method for the currentView Property.
     *
     * @return The object property stored in currentView.
     */
    public ObjectProperty<View> getCurrentViewProperty() {
        return currentView;
    }
}
