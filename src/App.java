import backend.DatabaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ui.HomePageController;

public class App extends Application {

    Stage window;
    Scene home;
    private DatabaseHandler databaseHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        databaseHandler = new DatabaseHandler();
        databaseHandler.login();
        window = primaryStage;
        window.setTitle("GUAVA");
        window.getIcons().add(new Image("file:src/ui/cssFiles/icons/guava.png"));
        homeScene();
    }

    private void homeScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ui/home.fxml"));
            Parent root = loader.load();
            HomePageController controller = loader.getController();
            controller.init();
            home = new Scene(root);
            home.getStylesheets().add(getClass().getResource("ui/cssFiles/home.css").toExternalForm());
            window.setScene(home);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
