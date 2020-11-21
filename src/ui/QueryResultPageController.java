package ui;

import backend.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;


public class QueryResultPageController {

    @FXML
    private Button homeButton;

    @FXML
    private Button recipesButton;

    @FXML
    private Button familyButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> resultList;

    private DatabaseHandler databaseHandler;
    private ArrayList<String> queryResult;

    public void init(DatabaseHandler databaseHandler, ArrayList<String> queryResult) {
        this.databaseHandler = databaseHandler;
        this.queryResult = queryResult;
        display();
    }

    private void display() {
        if (queryResult.size() == 0) {
            statusLabel.setText("Result size is 0");
            resultList.setVisible(false);
        } else {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.addAll(queryResult);
            resultList.setItems(list);
            resultList.setVisible(true);
        }
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
