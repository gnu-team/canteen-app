package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import model.exception.DataException;
import model.User;

/**
 * Handles events from the edit profile screen
 */
public class ProfileController implements MainAppReceiver,
        MainControllerReceiver {
    @FXML
    private TextArea bio;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;

    private MainFXApplication mainApp;
    private MainController mainController;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        // Populates editor fields with current values.
        // Can't do this in initialize() because initialize is called before
        // this method.
        User user = mainApp.getUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        bio.setText(user.getBio());
        email.setText(user.getEmail());
        address.setText(user.getAddress());
        phone.setText(user.getPhoneNumber());
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Saves profile information and tells MainFXApp to display the success
     * page.
     */
    public void handleSaveProfilePressed(ActionEvent actionEvent) {
        User user = mainApp.getUser();
        user.setFirstName(firstName.getText());
        user.setLastName(lastName.getText());
        user.setBio(bio.getText());
        user.setEmail(email.getText());
        user.setAddress(address.getText());
        user.setPhoneNumber(phone.getText());

        mainApp.getDataSource().updateUser(
            user,
            // Success
            () -> {
                mainApp.registrationComplete();
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );
    }
}
