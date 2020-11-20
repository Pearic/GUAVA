package ui;

import backend.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class LoadingScreenController extends SceneController {
    @FXML
    private Label mainLabel;

    @FXML
    private Label guavaLabel;

    @FXML
    private Label subLabel;

    @FXML
    private Button guavaLogo;

    @FXML
    private Button backgroundButton;

    @FXML
    private Button otherGuava;

    private DatabaseHandler databaseHandler;

    @Override
    public void init(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @FXML
    public void clicked(ActionEvent event) {
        switchScene("home", event, databaseHandler);
    }
}
