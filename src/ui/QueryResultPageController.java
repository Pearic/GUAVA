package ui;

import backend.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableView<ObservableList<String>> resultTable;

    private DatabaseHandler databaseHandler;
    private ArrayList<String> queryResult;

    public void init(DatabaseHandler databaseHandler, ArrayList<String> queryResult) {
        this.databaseHandler = databaseHandler;
        this.queryResult = queryResult;
        display();
    }

    // referred to https://stackoverflow.com/questions/13332212/javafx-tableview-dynamic-column-and-data-values
    private void display() {
        if (queryResult.size() == 1) {
            statusLabel.setText(queryResult.get(0));
            resultTable.setVisible(false);
        } else {
            resultTable.setVisible(true);
            String columns = queryResult.get(0);
            String[] columnsList = columns.split(",");

//        if (queryResult.size() == 0) {
//            statusLabel.setText("No result");
//            resultTable.setVisible(false);
//        } else {
//            resultTable.setVisible(true);
//            String columns = queryResult.get(0);
//            if (!columns.contains(",")) {
//                statusLabel.setText(columns);
//            }
//            String[] columnsList = columns.split(",");

            ObservableList<ObservableList<String>> rowData = FXCollections.observableArrayList();
            for (int i = 1; i < queryResult.size(); i++) {
                ObservableList<String> row = FXCollections.observableArrayList();
                String data = queryResult.get(i);
                String[] split = data.split(",");
                for (String str: split) {
                    row.add(str.trim());
                }
                rowData.add(row);
            }

            for (int i = 0; i < columnsList.length; i++) {
                int index = i;
                TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(columnsList[i]);
                tableColumn.setCellValueFactory(row -> new SimpleStringProperty(row.getValue().get(index)));
                resultTable.getColumns().add(tableColumn);
            }
            resultTable.setItems(rowData);
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
