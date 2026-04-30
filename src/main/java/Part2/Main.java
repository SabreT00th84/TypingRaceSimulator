package Part2;

import Part2.Views.MainMenuView;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        startRaceGUI();
    }

    public static void startRaceGUI() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Navigator navigator = new Navigator();
        AppState appState = new AppState();

        MainMenuView setupRaceView = new MainMenuView(appState, navigator);
        navigator.navigateTo(setupRaceView);
        Scene scene = new Scene(setupRaceView.getBody(), 1000, 500);
        scene.rootProperty().bind(Bindings.createObjectBinding(
                () -> navigator.getView().getBody(),
           navigator.getCurrentViewProperty()
        ));

        primaryStage.setTitle("Typing Race");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}