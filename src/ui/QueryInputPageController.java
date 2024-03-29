package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FoodStoredIn;
import model.SeasoningStoredIn;

import java.util.ArrayList;

public class QueryInputPageController {
    @FXML
    private Button backButton;

    @FXML
    private Button doneButton;

    @FXML
    private TextArea primaryLabel;

    @FXML
    private TextArea secondaryLabel;

    @FXML
    private TextField inputField;

    @FXML
    private Button queryImage;

    private DatabaseHandler databaseHandler;
    private String previousPage;
    private String query;

    public void init(DatabaseHandler databaseHandler, String previousPage, String query) {
        this.databaseHandler = databaseHandler;
        this.previousPage = previousPage;
        this.query = query;
        switch (this.query) {
            case "view":
                this.primaryLabel.setText("Enter the ID of the storage you want to view:");
                this.secondaryLabel.setText("");
                break;
            case "add food":
                this.primaryLabel.setText("Enter the information of the food with the following format:");
                this.secondaryLabel.setText("Food_Name,storageID");
                break;
            case "add seasoning":
                this.primaryLabel.setText("Enter the information of the seasoning with the following format:");
                this.secondaryLabel.setText("Seasoning_Name,storageID");
                break;
            case "remove food":
                this.primaryLabel.setText("Enter the name of the food you wish to remove and the storageID:");
                this.secondaryLabel.setText("Food_Name,storageID");
                break;
            case "remove seasoning":
                this.primaryLabel.setText("Enter the name of the seasoning you wish to remove and the storageID:");
                this.secondaryLabel.setText("Seasoning_Name,storageID");
                break;
            case "expiring":
                this.primaryLabel.setText("Enter the expiry date of the foods you wish to find:");
                this.secondaryLabel.setText("Enter date like: YYYY-MM-DD");
                break;
            case "info":
                this.primaryLabel.setText("Enter three attributes to get more information about all the food:");
                this.secondaryLabel.setText("Calories,Fat,Sodium,Carbohydrate,Protein,Vitamin,datePurchased,expiryDate");
                break;
            case "recipe find food":
                this.primaryLabel.setText("Enter the name of the food you wish to find in recipes:");
                this.secondaryLabel.setText("");
                break;
            case "recipe find seasoning":
                this.primaryLabel.setText("Enter the name of the seasoning you wish to find in recipes:");
                this.secondaryLabel.setText("");
                break;
            case "update recipe":
                this.primaryLabel.setText("Enter the name of the recipe and the name of the food you would like to change:");
                this.secondaryLabel.setText("recipe_name,food_name");
                break;
            case "calories less than":
                this.primaryLabel.setText("Enter the maximum amount of calories in a recipe:");
                this.secondaryLabel.setText("");
                break;
            default:
                System.out.println("invalid query");
                break;
        }
    }

    @FXML
    public void backButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(previousPage + ".fxml"));
            Parent root = loader.load();
            SceneController controller = loader.getController();
            controller.init(databaseHandler);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("cssFiles/" + previousPage + ".css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void doneButtonPressed(ActionEvent event) {
        String input = inputField.getText();
        this.query = query;
        ArrayList<String> result = new ArrayList<>();
        switch (this.query) {
            case "view":
                int storageIDView = Integer.parseInt(input);
                result = databaseHandler.showStoredIn(storageIDView);
                break;
            case "add food":
                String food_name = input.split(",")[0];
                int storageIDAddFood = Integer.parseInt(input.split(",")[1]);
                FoodStoredIn newFood = new FoodStoredIn(food_name, storageIDAddFood);
                result = databaseHandler.insertFoodToStorage(newFood);
                break;
            case "add seasoning":
                String seasoning_name = input.split(",")[0];
                int storageIDAddSeasoning = Integer.parseInt(input.split(",")[1]);
                SeasoningStoredIn newSeasoning = new SeasoningStoredIn(seasoning_name, storageIDAddSeasoning);
                result = databaseHandler.insertSeasoningToStorage(newSeasoning);
                break;
            case "remove food":
                String food_name_remove = input.split(",")[0];
                int storageIDRemoveFood = Integer.parseInt(input.split(",")[1]);
                FoodStoredIn removeFood = new FoodStoredIn(food_name_remove, storageIDRemoveFood);
                result = databaseHandler.removeFoodFromStorage(removeFood);
                break;
            case "remove seasoning":
                String seasoning_name_remove = input.split(",")[0];
                int storageIDRemoveSeasoning = Integer.parseInt(input.split(",")[1]);
                SeasoningStoredIn removeSeasoning = new SeasoningStoredIn(seasoning_name_remove, storageIDRemoveSeasoning);
                result = databaseHandler.removeSeasoningFromStorage(removeSeasoning);
                break;
            case "expiring":
                String expirationDate = input;
                result = databaseHandler.showFoodOnExpirationDate(expirationDate);
                break;
            case "info":
                String condition1 = input.split(",")[0];
                String condition2 = input.split(",")[1];
                String condition3 = input.split(",")[2];
                result = databaseHandler.showNutritionalValueOfFood(condition1, condition2, condition3);
                break;
            case "recipe find food":
                String foodName = input;
                result = databaseHandler.showRecipeWhichContainsFood(foodName);
                break;
            case "recipe find seasoning":
                String seasoningName = input;
                result = databaseHandler.showRecipeWhichContainsSeasoning(seasoningName);
                break;
            case "update recipe":
                String recipeName = input.split(",")[0];
                String foodNameUpdate = input.split(",")[1];
                result = databaseHandler.updateRecipe(recipeName, foodNameUpdate);
                break;
            case "calories less than":
                int calories = Integer.parseInt(input);
                result = databaseHandler.showRecipeWithLessCalories(calories);
                break;
            default:
                System.out.println("invalid query");
                break;
        }
        switchToResultPage(event, result);
    }

    private void switchToResultPage(ActionEvent event, ArrayList<String> queryResult) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource( "queryResult.fxml"));
            Parent root = loader.load();
            QueryResultPageController controller = loader.getController();
            controller.init(databaseHandler, queryResult);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("cssFiles/queryResult.css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
