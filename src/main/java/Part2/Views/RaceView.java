package Part2.Views;

import Part2.Models.Typist;
import Part2.View;
import Part2.ViewModels.RaceViewModel;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RaceView extends View {

    private RaceViewModel viewModel;

    public RaceView(Typist[] typists, boolean autocorrect, String passage) {
        super(typists, autocorrect, passage);
        viewModel.startRace();
    }

    @Override
    protected void init(Object... o) {
        viewModel = new RaceViewModel((Typist[]) o[0], (boolean) o[1], (String) o[2]);
    }

    @Override
    public Parent build() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));

        VBox progressSection = new VBox(2.5);

        for (Typist typist : viewModel.getTypists()) {
            HBox typistLane = new HBox(2.5, new Label(typist.getName()));

            StackPane progress = new StackPane();
            progress.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(progress, javafx.scene.layout.Priority.ALWAYS);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            progressBar.progressProperty().bind(Bindings.createDoubleBinding(
                    () -> ((double) typist.getProgress() / viewModel.getPassage().length()),
                    typist.getProgressProperty()
            ));
            progressBar.setStyle("-fx-accent: " + typist.getColour());

            Label symbol = new Label(typist.getEmojiSymbol());
            symbol.translateXProperty().bind(Bindings.createDoubleBinding(
                    () -> (progressBar.getProgress() * progressBar.getWidth()),
                    progressBar.progressProperty(), progressBar.widthProperty()
            ));
            progress.getChildren().addAll(progressBar, symbol);

            Label info = new Label();
            typistLane.getChildren().add(progress);
            progressSection.getChildren().add(typistLane);
        }

        vbox.getChildren().add(progressSection);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }
}
