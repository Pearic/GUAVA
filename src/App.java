import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.homePageController;

public class App extends Application {

    Stage window;
    Scene home;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("GUAVA");
        homeScene();
    }

    private void homeScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ui/home.fxml"));
            Parent root = loader.load();
            homePageController controller = loader.getController();
            home = new Scene(root);
            home.getStylesheets().add(getClass().getResource("cssFiles/home.css").toExternalForm());
            window.setScene(home);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
