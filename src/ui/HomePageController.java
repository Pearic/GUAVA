package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

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

    @FXML
    private Button foodCountButton;

    @FXML
    private Button iconContainer;

    private DatabaseHandler databaseHandler;

    public void init(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        this.homeLabel.setUnderline(true);
        this.recipesLabel.setUnderline(false);
        this.familyLabel.setUnderline(false);
    }

    @FXML
    public void viewStorageButtonPressed(ActionEvent event) {
        // System.out.println("view");
        switchToQueryInputScene("home", "view", event, databaseHandler);
    }

    @FXML
    public void addFoodButtonPressed(ActionEvent event) {
        // System.out.println("add food");
        switchToQueryInputScene("home", "add food", event, databaseHandler);
    }

    @FXML
    public void addSeasoningButtonPressed(ActionEvent event) {
        // System.out.println("add seasoning");
        switchToQueryInputScene("home", "add seasoning", event, databaseHandler);
    }

    @FXML
    public void removeFoodButtonPressed(ActionEvent event) {
        // System.out.println("remove food");
        switchToQueryInputScene("home", "remove food", event, databaseHandler);
    }

    @FXML
    public void removeSeasoningButtonPressed(ActionEvent event) {
        // System.out.println("remove seasoning");
        switchToQueryInputScene("home", "remove seasoning", event, databaseHandler);
    }

    @FXML
    public void expiringFoodButtonPressed(ActionEvent event) {
        // System.out.println("expiring");
        switchToQueryInputScene("home", "expiring", event, databaseHandler);
    }

    @FXML
    public void foodInfoButtonPressed(ActionEvent event) {
        // System.out.println("info");
        switchToQueryInputScene("home", "info", event, databaseHandler);
    }

    @FXML
    public void foodCountButtonPressed(ActionEvent event) {
        ArrayList<String> result = new ArrayList<>();
        // System.out.println("food count");
        result = databaseHandler.showCountOfFoodInStorage();
        switchToResultPage(event, result);
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
