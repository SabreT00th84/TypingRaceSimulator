package Part2.Views;

import Part2.AppState;
import Part2.Navigator;
import Part2.View;
import Part2.ViewModels.AddTypistsViewModel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AddTypistsView extends View {
    private AppState appState;
    private Navigator navigator;
    private AddTypistsViewModel viewModel;

    public AddTypistsView(AppState appState, Navigator navigator, Integer numOfTypists) {
        super(appState, navigator, numOfTypists);
    }

    @Override
    protected void init(Object... o) {
        appState = (AppState) o[0];
        navigator = (Navigator) o[1];
        viewModel = new AddTypistsViewModel(appState, (Integer) o[2]);
    }

    @Override
    protected Parent build() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));

        for (int i = 0; i < viewModel.getNumOfTypists(); i++) {
            Label heading = new Label("Typist" + (i + 1));
            heading.setFont(Font.font("sans-serif", FontWeight.BOLD, 20));

            TextField nameField = new TextField();

            TextField symbolField = new TextField();
            symbolField.setPrefColumnCount(2);
            symbolField.setMaxWidth(Region.USE_PREF_SIZE);

            ColorPicker colourPicker = new ColorPicker();

            ChoiceBox<String> styles = new ChoiceBox<>();
            styles.getItems().addAll(viewModel.getStyles());
            styles.setValue(viewModel.getStyles().iterator().next());

            ChoiceBox<String> keyboards = new ChoiceBox<>();
            keyboards.getItems().addAll(viewModel.getKeyboards());
            keyboards.setValue(viewModel.getKeyboards().iterator().next());

            CheckBox wristSupport = new CheckBox("Wrist Support (-1 burnout duration)");
            CheckBox energyDrink = new CheckBox("Energy Drink (+10% accuracy for 75 turns, " +
                    "-10% accuracy for the rest of the race)");
            CheckBox headphones = new CheckBox("Noise-Cancelling Headphones (-5% Mistype Chance)");

            Button addButton = new Button("Add");

            Label errorMessage = new Label();
            errorMessage.setStyle("-fx-text-fill: red");
            errorMessage.setVisible(false);

            HBox buttonGroup = new HBox(5, addButton);
            Label success = new Label("✅");
            success.setVisible(false);
            buttonGroup.getChildren().add(success);

            addButton.setOnAction(e -> {
                String result = viewModel.addTypist(
                        nameField.getText(),
                        symbolField.getText(),
                        viewModel.colourToHex(
                                colourPicker.getValue().getRed(),
                                colourPicker.getValue().getGreen(),
                                colourPicker.getValue().getBlue()
                                ),
                        styles.getValue(),
                        keyboards.getValue(),
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
                    addButton.setVisible(false);
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

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            String validationResult = viewModel.validateAllTypists();

            if (validationResult == null) {
                error.setVisible(false);
                navigator.navigateTo(new MainMenuView(appState, navigator));
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
                backButton
        );

        return new ScrollPane(vbox);
    }
}
