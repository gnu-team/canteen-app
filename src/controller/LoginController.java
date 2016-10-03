package controller;

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

    private void handleCloseMenu() {
        mainApp.close();
    }

    public void handleLoginPressed(ActionEvent actionEvent) {
        try {
            User user = DataSource.getInstance().authenticate(
                username.getText(), password.getText());

            mainApp.loginComplete(user);
        } catch (NoSuchUserException e) {
            mainApp.showAlert(e.getMessage());
        }
    }

    public void handleBackButtonPressed(ActionEvent actionEvent) {
        mainApp.showRegister();
    }
}
