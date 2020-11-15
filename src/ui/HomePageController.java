package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomePageController extends SceneController {
    @FXML
    private Label homeLabel;

    @FXML
    private Label recipesLabel;

    @FXML
    private Label familyLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button recipesButton;

    @FXML
    private Button familyButton;

    public void init() {}

    @FXML
    public void homeButtonPressed(ActionEvent event) {
        switchScene("home", event);
    }

    @FXML
    public void recipeButtonPressed(ActionEvent event) {
        switchScene("recipes", event);
    }

    @FXML
    public void familyButtonPressed(ActionEvent event) {
        switchScene("family", event);
    }

}
