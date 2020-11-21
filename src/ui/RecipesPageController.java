package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RecipesPageController extends SceneController {
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
    private Button recipeFindFoodButton;

    @FXML
    private Button recipeFindSeasoningButton;

    @FXML
    private Button updateRecipeButton;

    @FXML
    private Button caloriesLessThanButton;

    @FXML
    private Button iconContainer;

    private DatabaseHandler databaseHandler;

    public void init(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        this.homeLabel.setUnderline(false);
        this.recipesLabel.setUnderline(true);
        this.familyLabel.setUnderline(false);
    }

    @FXML
    public void recipeFindFoodButtonPressed(ActionEvent event) {
        // System.out.println("recipe find food");
        switchToQueryInputScene("recipes", "recipe find food", event, databaseHandler);
    }

    @FXML
    public void recipeFindSeasoningButtonPressed(ActionEvent event) {
        // System.out.println("recipe find seasoning");
        switchToQueryInputScene("recipes", "recipe find seasoning", event, databaseHandler);
    }

    @FXML
    public void updateRecipeButtonPressed(ActionEvent event) {
        System.out.println("update recipe");
        switchToQueryInputScene("recipes", "update recipe", event, databaseHandler);
    }

    @FXML
    public void caloriesLessThanButtonPressed(ActionEvent event) {
        System.out.println("calories less than");
        switchToQueryInputScene("recipes", "calories less than", event, databaseHandler);
    }

    @FXML
    public void homeButtonPressed(ActionEvent event) {
        switchScene("home", event, databaseHandler);
    }

    @FXML
    public void recipeButtonPressed(ActionEvent event) {
        //nothing
    }

    @FXML
    public void familyButtonPressed(ActionEvent event) {
        switchScene("family", event, databaseHandler);
    }

}
