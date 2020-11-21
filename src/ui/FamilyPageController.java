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
        // System.out.println("non picky");
        ArrayList<String> result = new ArrayList<>();
        result = databaseHandler.peopleWhoLikesAllFood();
        switchToResultPage(event, result);
    }

    @FXML
    public void pickyEaterButtonPressed(ActionEvent event) {
        // System.out.println("picky");
        ArrayList<String> result = new ArrayList<>();
        result = databaseHandler.peopleWhoDislikesAllFood();
        switchToResultPage(event, result);
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
