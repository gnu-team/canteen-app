package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MainFXApplication extends Application {
    // TODO: Put this in a ResourceBundle instead
    private static final String TITLE = "Canteen: A Water Tracker";

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;

        primaryStage.setTitle(TITLE);
        showWelcomeScreen();
        primaryStage.show();
    }

    private void showWelcomeScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/welcome.fxml"));
        stage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
