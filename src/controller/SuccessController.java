package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles events from the success screen
 */
public class SuccessController implements IMainAppReceiver {
    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleLogoutPressed(ActionEvent event) {
        mainApp.logout();
    }
}
