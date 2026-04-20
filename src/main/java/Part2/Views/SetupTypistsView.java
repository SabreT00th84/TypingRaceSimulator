package Part2.Views;

import Part2.Models.RaceConfig;
import Part2.NavigationView;
import Part2.ViewModels.SetupTypistsViewModel;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class SetupTypistsView extends NavigationView {
    private SetupTypistsViewModel viewModel;

    public SetupTypistsView(RaceConfig config) {
        super(config);
    }

    @Override
    protected void init(Object... o) {
        viewModel = new SetupTypistsViewModel((RaceConfig) Arrays.stream(o).findFirst().get());
    }

    @Override
    protected Parent build() {
        return new VBox(new Text("" + viewModel.config.numOfTypists()));
    }
}
