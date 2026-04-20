package Part2;

import javafx.scene.Parent;

public abstract class View {

    private final Parent body;

    /**
     * Constructor of View class. All subclasses should
     * initialise variables using init() and build the view
     * at initialisation.
     *
     * @param o 0 or more objects to pass to the init method.
     */
    protected View(Object... o) {
        this.init(o);
        this.body = build();
    }

    /**
     * An abstract method to allow subclasses to initialise
     * subclass variables without needing to use the constructor.
     *
     * @param o 0 or more objects passed in from the constructor.
     */
    protected abstract void init(Object... o);

    /**
     * An abstract method to allow subclasses to define
     * code that will build the JavaFX view content.
     *
     * @return view content as a Parent node.
     */
    protected abstract Parent build();

    /**
     * A getter method for the body variable
     *
     * @return view content as a Parent node
     */
    public final Parent getBody() {
        return body;
    }
}