package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Handles events from the welcome screen
 */
public class LoginController implements MainAppReceiver {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * When user presses login, attempt to log in
     */
    public void handleLoginPressed() {
        mainApp.getDataSource().authenticate(
            usernameField.getText(), passwordField.getText(),
            // Success
            user -> {
                mainApp.loginComplete(user);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );
    }

    /**
     * When user presses register, show registration screen
     */
    public void handleRegisterPressed() {
        mainApp.showRegister();
    }
}
