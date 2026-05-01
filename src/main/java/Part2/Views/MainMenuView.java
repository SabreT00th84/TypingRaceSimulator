package Part2.Views;

import Part2.AppState;
import Part2.Navigator;
import Part2.View;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenuView extends View {

    private AppState appState;
    private Navigator navigator;

    public MainMenuView(AppState appState, Navigator navigator) {
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

        Label errorMessage = new Label("Please add at least 2 typists to start the race.");
        errorMessage.setStyle("-fx-text-fill: red");
        errorMessage.visibleProperty().bind(appState.typistsProperty().sizeProperty().lessThan(2));

        Button addTypists = new Button("Add More Typists");
        addTypists.setOnAction(e -> navigator.navigateTo(new NumOfTypistsView(appState, navigator)));

        Button editTypists = new Button("Edit Current Typists");
        editTypists.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThan(0));
        editTypists.setOnAction(e -> navigator.navigateTo(new EditTypistsView(appState, navigator)));

        Button startRace = new Button("Start Race");
        startRace.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThanOrEqualTo(2));
        startRace.setOnAction(e -> navigator.navigateTo(new SetupRaceView(appState, navigator)));

        Button viewStats = new Button("View Typist Stats");
        viewStats.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThan(0));
        viewStats.setOnAction(e -> navigator.navigateTo(new StatsView(appState, navigator)));

        Button compare = new Button("Compare Typists");
        compare.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThanOrEqualTo(2));
        compare.setOnAction(e -> navigator.navigateTo(new CompareView(appState, navigator)));

        Button viewLeaderboard = new Button("View Leaderboard");
        viewLeaderboard.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThan(0));
        viewLeaderboard.setOnAction(e -> navigator.navigateTo(new LeaderboardView(appState, navigator)));

        Button viewFinLeaderboard = new Button("View Financial Leaderboard");
        viewLeaderboard.visibleProperty().bind(appState.typistsProperty().sizeProperty().greaterThan(0));
        viewLeaderboard.setOnAction(e -> navigator.navigateTo(new FinLeaderboardView(appState, navigator)));

        vbox.getChildren().addAll(
                errorMessage,
                addTypists,
                editTypists,
                startRace,
                viewStats,
                compare,
                viewLeaderboard,
                viewFinLeaderboard
        );

        return vbox;
    }
}
