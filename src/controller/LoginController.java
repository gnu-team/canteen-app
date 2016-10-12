package controller;

import exception.DataBackendException;
import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import model.User;
import model.DataSource;
import exception.NoSuchUserException;

/**
 * Handles events from the login screen
 */
public class LoginController implements IMainAppReceiver {
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * When user presses login button, attempts to log in.
     *
     * If there's an authentication failure, shows a whiny alert.
     */
    public void handleLoginPressed(ActionEvent actionEvent) {
        try {
            User user = mainApp.getDataSource().authenticate(
                username.getText(), password.getText());

            mainApp.loginComplete(user);
        } catch (NoSuchUserException e) {
            mainApp.showAlert(e.getMessage());
        } catch (DataBackendException e) {
            e.printStackTrace();
            mainApp.showAlert(e.getMessage());
        }
    }

    /**
     * Allows user to cancel login and return to the registration screen.
     */
    public void handleBackButtonPressed(ActionEvent actionEvent) {
        mainApp.showRegister();
    }
}
