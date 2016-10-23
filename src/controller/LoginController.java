package controller;

import model.exception.DataException;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import model.User;

/**
 * Handles events from the welcome screen
 */
public class LoginController implements MainAppReceiver {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label Welcome;

    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * When user presses login, attempt to log in
     */
    public void handleLoginPressed(ActionEvent event) throws Exception {
        User user;

        try {
            user = mainApp.getDataSource().authenticate(usernameField.getText(),
                                                        passwordField.getText());
        } catch (DataException e) {
            e.printStackTrace();
            mainApp.showAlert(e.getMessage());
            return;
        }

        mainApp.loginComplete(user);
    }

    /**
     * When user presses register, show registration screen
     */
    public void handleRegisterPressed(ActionEvent actionEvent) {
        mainApp.showRegister();
    }
}
