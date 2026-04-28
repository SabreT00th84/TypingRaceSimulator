package Part2.Views;

import Part2.Models.RaceConfig;
import Part2.Navigator;
import Part2.View;
import Part2.ViewModels.SetupTypistsViewModel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;

public class SetupTypistsView extends View {
    private SetupTypistsViewModel viewModel;
    private Navigator navigator;

    public SetupTypistsView(RaceConfig config, Navigator navigator) {
        super(config, navigator);
    }

    @Override
    protected void init(Object... o) {
        viewModel = new SetupTypistsViewModel((RaceConfig) o[0]);
        navigator = (Navigator) o[1];
    }

    @Override
    protected Parent build() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));

        for (int i = 0; i < viewModel.getConfig().numOfTypists(); i++) {
            Label heading = new Label("Typist" + (i + 1));
            heading.setFont(Font.font("sans-serif", FontWeight.BOLD, 20));

            TextField nameField = new TextField();

            TextField symbolField = new TextField();
            symbolField.setTextFormatter(new TextFormatter<>(change -> {return change;}));
            symbolField.setPrefColumnCount(2);
            symbolField.setMaxWidth(Region.USE_PREF_SIZE);
            symbolField.setStyle("-fx-font-family: 'Apple Color Emoji';");

            ColorPicker colourPicker = new ColorPicker();

            ChoiceBox<String> styles = new ChoiceBox<>();
            styles.getItems().addAll(viewModel.getStyles());
            styles.setValue(viewModel.getStyles().iterator().next());

            ChoiceBox<String> keyboards = new ChoiceBox<>();
            keyboards.getItems().addAll(viewModel.getKeyboards());
            keyboards.setValue(viewModel.getKeyboards().iterator().next());

            CheckBox wristSupport = new CheckBox("Wrist Support (-1 burnout duration)");
            CheckBox energyDrink = new CheckBox("Energy Drink (+10% accuracy for 15w2 turns, " +
                    "-10% accuracy for the rest of the race)");
            CheckBox headphones = new CheckBox("Noise-Cancelling Headphones (+5% accuracy)");

            Button addButton = new Button("Add");
            int index = i;

            Label errorMessage = new Label();
            errorMessage.setStyle("-fx-text-fill: red");
            errorMessage.setVisible(false);

            HBox buttonGroup = new HBox(5, addButton);
            Label success = new Label("✅");
            success.setVisible(false);
            buttonGroup.getChildren().add(success);

            addButton.setOnAction(e -> {
                String result = viewModel.addTypist(index,
                        nameField.getText(),
                        symbolField.getText(),
                        viewModel.colourToHex(
                                colourPicker.getValue().getRed(),
                                colourPicker.getValue().getGreen(),
                                colourPicker.getValue().getBlue()
                                ),
                        viewModel.getStyleAccuracy(styles.getValue()) +
                                viewModel.getKeyboardAccuracy(keyboards.getValue()),
                        viewModel.getKeyboardSpeed(keyboards.getValue()),
                        wristSupport.isSelected(),
                        energyDrink.isSelected(),
                        headphones.isSelected()
                        );

                if (result != null) {
                    errorMessage.setText(result);
                    errorMessage.setVisible(true);
                } else {
                    errorMessage.setVisible(false);
                    success.setVisible(true);
                }
            });

            vbox.getChildren().addAll(
                    heading,
                    new Label("Name"),
                    nameField,
                    new Label("Symbol/Emoji"),
                    symbolField,
                    new Label("Colour"),
                    colourPicker,
                    new Label("Typing Style"),
                    styles,
                    new Label("Keyboard"),
                    keyboards,
                    new Label("Accessories"),
                    wristSupport,
                    energyDrink,
                    headphones,
                    errorMessage,
                    buttonGroup
            );
        }

        Label error = new Label();
        error.setVisible(false);

        Button startButton = new Button("Start Race");
        startButton.setOnAction(e -> {
            String validationResult = viewModel.validateAllTypists();

            if (validationResult == null) {
                error.setVisible(false);
                navigator.navigateTo(new RaceView(
                        viewModel.getTypists(),
                        viewModel.getConfig().autocorrect(),
                        viewModel.getConfig().passage()
                ));
            } else {
                error.setText(validationResult);
                error.setVisible(true);
                error.setStyle("-fx-text-fill: red");
            }
        });

        Region spacer = new Region();
        spacer.setPadding(new Insets(10));

        vbox.getChildren().addAll(
                error,
                startButton
        );

        return new ScrollPane(vbox);
    }
}
