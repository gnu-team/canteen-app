package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.User;

/**
 * Handles events from the edit profile screen
 */
public class ProfileController implements IMainAppReceiver {
    @FXML
    private TextArea bio;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;

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
