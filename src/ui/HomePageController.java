package ui;

import backend.DatabaseHandler;
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

    @FXML
    private Button viewStorageButton;

    @FXML
    private Button addFoodButton;

    @FXML
    private Button addSeasoningButton;

    @FXML
    private Button removeFoodButton;

    @FXML
    private Button removeSeasoningButton;

    @FXML
    private Button expiringFoodButton;

    @FXML
    private Button foodInfoButton;

    private DatabaseHandler databaseHandler;

    public void init(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @FXML
    public void viewStorageButtonPressed(ActionEvent event) {
        System.out.println("view");
    }

    @FXML
    public void addFoodButtonPressed(ActionEvent event) {
        System.out.println("add food");
    }

    @FXML
    public void addSeasoningButtonPressed(ActionEvent event) {
        System.out.println("add seasoning");
    }

    @FXML
    public void removeFoodButtonPressed(ActionEvent event) {
        System.out.println("remove food");
    }

    @FXML
    public void removeSeasoningButtonPressed(ActionEvent event) {
        System.out.println("remove seasoning");
    }

    @FXML
    public void expiringFoodButtonPressed(ActionEvent event) {
        System.out.println("expiring");
    }

    @FXML
    public void foodInfoButtonPressed(ActionEvent event) {
        System.out.println("info");
    }

    @FXML
    public void homeButtonPressed(ActionEvent event) {
        //nothing
    }

    @FXML
    public void recipeButtonPressed(ActionEvent event) {
        switchScene("recipes", event, databaseHandler);
    }

    @FXML
    public void familyButtonPressed(ActionEvent event) {
        switchScene("family", event, databaseHandler);
    }

}
