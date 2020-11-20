import backend.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.LoadingScreenController;

public class App extends Application {

    Stage window;
    Scene home;
    private DatabaseHandler databaseHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        databaseHandler = new DatabaseHandler();
        databaseHandler.login();
        window = primaryStage;
        window.setTitle("GUAVA");
        window.getIcons().add(new Image("file:src/ui/cssFiles/icons/bigguava.png"));
        loadingScene();
        // databaseHandler.createTable();
    }

    @Override
    public void stop() {
        databaseHandler.close();
    }

    private void loadingScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ui/loading.fxml"));
            Parent root = loader.load();
            LoadingScreenController controller = loader.getController();
            controller.init(databaseHandler);
            home = new Scene(root);
            home.getStylesheets().add(getClass().getResource("ui/cssFiles/loading.css").toExternalForm());
            window.setScene(home);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
