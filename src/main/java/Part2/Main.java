package Part2;

import Part2.Views.SetupView;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SetupView setupView = new SetupView();
        Scene scene = new Scene(setupView.getBody(), 1000, 500);
        scene.rootProperty().bind(Bindings.createObjectBinding(
                () -> setupView.getCurrentViewProperty().get().build(),
           setupView.getCurrentViewProperty()
        ));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}