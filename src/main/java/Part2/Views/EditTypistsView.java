package Part2.Views;

import Part2.AppState;
import Part2.Models.Typist;
import Part2.Navigator;
import Part2.View;
import Part2.ViewModels.EditTypistsViewModel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EditTypistsView extends View {
    private AppState appState;
    private Navigator navigator;
    private EditTypistsViewModel viewModel;

    public EditTypistsView(AppState appState, Navigator navigator) {
        super(appState, navigator);
    }

    @Override
    protected void init(Object... o) {
        this.appState = (AppState) o[0];
        this.navigator = (Navigator) o[1];
        this.viewModel = new EditTypistsViewModel();
    }

    @Override
    protected Parent build() {
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(10));

        for (Typist typist : appState.getTypists()) {
            Label heading = new Label(typist.getName());
            heading.setFont(Font.font("sans-serif", FontWeight.BOLD, 20));

            Button remove = new Button("Remove");
            remove.setOnAction(e -> {
                appState.getTypists().remove(typist);
                navigator.navigateTo(new EditTypistsView(appState, navigator));
            });

            TextField nameField = new TextField(typist.getName() + "(" + typist.getCoins() + " coins)");
            nameField.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.isBlank()) typist.setName(newValue);
                    }
            );

            TextField symbolField = new TextField(typist.getSymbol());
            symbolField.setPrefColumnCount(2);
            symbolField.setMaxWidth(Region.USE_PREF_SIZE);
            symbolField.textProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.isBlank()) typist.setSymbol(newValue);
                    }
            );

            ColorPicker colourPicker = new ColorPicker(Color.web(typist.getColour()));
            colourPicker.valueProperty().addListener(
                    (observable, oldValue, newValue) ->
                            typist.setColour(viewModel.colourToHex(
                                    newValue.getRed(),
                                    newValue.getGreen(),
                                    newValue.getBlue()
                            ))
            );

            ChoiceBox<String> styles = new ChoiceBox<>();
            styles.getItems().addAll(viewModel.getStyles());
            styles.setValue(typist.getTypingStyle());
            styles.valueProperty().addListener(
                    (observable, oldValue, newValue) ->
                            typist.setTypingStyle(newValue)
            );

            ChoiceBox<String> keyboards = new ChoiceBox<>();
            keyboards.getItems().addAll(viewModel.getKeyboards());
            keyboards.setValue(typist.getKeyboardType());
            keyboards.valueProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        boolean keyboardPurchased = typist.spendCoins(Typist.keyboardTypes.get(newValue)[2].intValue());
                        if (keyboardPurchased) {
                            typist.setKeyboardType(newValue);
                        }
                    }
            );

            CheckBox wristSupport = new CheckBox("Wrist Support (-1 burnout duration)");
            wristSupport.setSelected(typist.hasWristSupport());
            wristSupport.selectedProperty().addListener(
                    (observable, oldValue, newValue) ->
                            typist.setWristSupport(newValue)
            );

            CheckBox energyDrink = new CheckBox("Energy Drink (+10% accuracy for 75 turns, " +
                    "-10% accuracy for the rest of the race)");
            energyDrink.setSelected(typist.hasEnergyDrink());
            energyDrink.selectedProperty().addListener(
                    (observable, oldValue, newValue) ->
                            typist.setEnergyDrink(newValue)
            );

            CheckBox headphones = new CheckBox("Noise-Cancelling Headphones (-5% Mistype Chance)");
            headphones.setSelected(typist.hasHeadphones());
            headphones.selectedProperty().addListener(
                    (observable, oldValue, newValue) ->
                            typist.setHeadphones(newValue)
            );

            CheckBox sponsored = new CheckBox("KeyCorp: +50 coins if you finish without a single burnout");
            sponsored.selectedProperty().bindBidirectional(typist.getSponsoredProperty());

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
                    new Label("Sponsorships"),
                    sponsored
            );
        }

        Button update = new Button("Update");
        update.setOnAction(e -> navigator.navigateTo(new MainMenuView(appState, navigator)));

        vbox.getChildren().add(update);

        return new ScrollPane(vbox);
    }
}
