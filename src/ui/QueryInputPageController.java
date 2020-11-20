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
                this.primaryLabel.setText("Enter the name of the food you wish to find more information about:");
                this.secondaryLabel.setText("");
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
                this.primaryLabel.setText("Enter the name of the recipe and ingredients you wish to update:");
                this.secondaryLabel.setText("recipe_name,seasoning_name,food_name");
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
        switch (this.query) {
            case "view":
                int storageIDView = Integer.parseInt(input);
                databaseHandler.showStoredIn(storageIDView);
                break;
            case "add food":
                String food_name = input.split(",")[0];
                int storageIDAddFood = Integer.parseInt(input.split(",")[1]);
                FoodStoredIn newFood = new FoodStoredIn(food_name, storageIDAddFood);
                databaseHandler.insertFoodToStorage(newFood);
                break;
            case "add seasoning":
                String seasoning_name = input.split(",")[0];
                int storageIDAddSeasoning = Integer.parseInt(input.split(",")[1]);
                SeasoningStoredIn newSeasoning = new SeasoningStoredIn(seasoning_name, storageIDAddSeasoning);
                databaseHandler.insertSeasoningToStorage(newSeasoning);
                break;
            case "remove food":
                String food_name_remove = input.split(",")[0];
                int storageIDRemoveFood = Integer.parseInt(input.split(",")[1]);
                FoodStoredIn removeFood = new FoodStoredIn(food_name_remove, storageIDRemoveFood);
                databaseHandler.removeFoodFromStorage(removeFood);
                break;
            case "remove seasoning":
                String seasoning_name_remove = input.split(",")[0];
                int storageIDRemoveSeasoning = Integer.parseInt(input.split(",")[1]);
                SeasoningStoredIn removeSeasoning = new SeasoningStoredIn(seasoning_name_remove, storageIDRemoveSeasoning);
                databaseHandler.removeSeasoningFromStorage(removeSeasoning);
                break;
            case "expiring":
                this.primaryLabel.setText("Enter the expiry date of the foods you wish to find:");
                this.secondaryLabel.setText("Enter date like: YYYY-MM-DD");
                break;
            case "info":
                this.primaryLabel.setText("Enter the name of the food you wish to find more information about:");
                this.secondaryLabel.setText("");
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
                this.primaryLabel.setText("Enter the name of the recipe and ingredients you wish to update:");
                this.secondaryLabel.setText("recipe_name,seasoning_name,food_name");
                break;
            case "calories less than":
                this.primaryLabel.setText("Enter the maximum amount of calories in a recipe:");
                this.secondaryLabel.setText("");
                break;
            default:
                System.out.println("invalid query");
                break;
        }
        switchToResultPage(event, "");
    }

    private void switchToResultPage(ActionEvent event, String queryResult) {
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
