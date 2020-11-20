package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FamilyPageController extends SceneController{
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

    @FXML
    private Button nonPickyEaterButton;

    @FXML
    private Button pickyEaterButton;

    @FXML
    private Button oldestButton;

    @FXML
    private Button iconContainer;

    private DatabaseHandler databaseHandler;

    public void init(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        this.homeLabel.setUnderline(false);
        this.recipesLabel.setUnderline(false);
        this.familyLabel.setUnderline(true);
    }

    @FXML
    public void nonPickyEaterButtonPressed(ActionEvent event) {
        System.out.println("non picky");
    }

    @FXML
    public void pickyEaterButtonPressed(ActionEvent event) {
        System.out.println("picky");
    }

    @FXML
    public void oldestButtonPressed(ActionEvent event) {
        System.out.println("oldest");
    }

    @FXML
    public void homeButtonPressed(ActionEvent event) {
        switchScene("home", event, databaseHandler);
    }

    @FXML
    public void recipeButtonPressed(ActionEvent event) {
        switchScene("recipes", event, databaseHandler);
    }

    @FXML
    public void familyButtonPressed(ActionEvent event) {
        //nothing
    }

}
