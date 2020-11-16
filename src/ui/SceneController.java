package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class SceneController {
    abstract void init(DatabaseHandler databaseHandler);

    public void switchScene(String sceneName, ActionEvent event, DatabaseHandler databaseHandler) {
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
