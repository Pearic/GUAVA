package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class QueryResultPageController {

    @FXML
    private Button homeButton;

    @FXML
    private Button recipeButton;

    @FXML
    private Button familyButton;

    private DatabaseHandler databaseHandler;
    private String queryResult;

    public void init(DatabaseHandler databaseHandler, String queryResult) {
        this.databaseHandler = databaseHandler;
        this.queryResult = queryResult;
        System.out.println("result page");
    }

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

    public void switchScene(String sceneName, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(sceneName + ".fxml"));
            Parent root = loader.load();
            SceneController controller = loader.getController();
            controller.init(databaseHandler);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("cssFiles/" + sceneName + ".css").toExternalForm());
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
