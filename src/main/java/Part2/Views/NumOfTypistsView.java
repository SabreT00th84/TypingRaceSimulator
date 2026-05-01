package Part2.Views;

import Part2.AppState;
import Part2.Navigator;
import Part2.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class NumOfTypistsView extends View {

    private AppState appState;
    private Navigator navigator;

    public NumOfTypistsView(AppState appState, Navigator navigator) {
        super(appState, navigator);
    }

    @Override
    protected void init(Object... o) {
        this.appState = (AppState) o[0];
        this.navigator = (Navigator) o[1];
    }

    @Override
    protected Parent build() {
        VBox vbox = new VBox(5);
        vbox.setAlignment(Pos.CENTER);

        Spinner<Integer> numOfTypists = new Spinner<>(1, 6, 2);

        Button submit = new Button("Submit");
        submit.setOnAction(e ->
            navigator.navigateTo(new AddTypistsView(
                appState,
                navigator,
                numOfTypists.getValue()
            ))
        );

        vbox.getChildren().addAll(new Label("Number Of Typists"), numOfTypists, submit);

        return vbox;
    }
}
