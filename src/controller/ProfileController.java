package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import model.User;

/**
 * Handles events from the edit profile screen
 */
public class EditProfileController implements IMainAppReceiver {
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
     * Displays an error when user presses "Save Profile."
     */
    public void handleSaveProfilePressed(ActionEvent actionEvent) {
        mainApp.showAlert("Saving profile is not implemented yet.");
    }
}
