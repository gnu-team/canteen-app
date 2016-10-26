package controller;

import exception.DataBackendException;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.User;
import model.UserFactory;
import model.AccountType;
import exception.InvalidUserException;

/**
 * Handles events from the registration screen
 */
public class RegisterController implements MainAppReceiver {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<AccountType> accountTypeBox;

    private MainFXApplication mainApp;

    /**
     * Populates the account type combobox.
     */
    @FXML
    private void initialize() {
        accountTypeBox.getItems().setAll(AccountType.values());
        // If we don't explicitly set a default, a JFoenix combobox
        // apparently doesn't choose anything, which can result in a
        // null value when user hits Register.
        // So instead of checking if accountTypeBox.getValue() == null
        // whenever user presses Register and showing an alert if it
        // doesn't, choose a default value (currently USER) so
        // getValue() == null cannot happen.
        // The downside of this is that you can't see the "Account Type"
        // helper text anymore.
        accountTypeBox.setValue(AccountType.DEFAULT);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * When user presses register button, attempts to register.
     */
    public void handleRegisterPressed(ActionEvent actionEvent) {
        User user;
        try {
            user = UserFactory.createUser(usernameField.getText(),
                                          passwordField.getText(),
                                          accountTypeBox.getValue());
            mainApp.getDataSource().addUser(user);
        } catch (InvalidUserException e) {
            mainApp.showAlert(e.getMessage());
            return;
        } catch (DataBackendException e) {
            e.printStackTrace();
            mainApp.showAlert(e.getMessage());
            return;
        }

        mainApp.finishRegistration(user);
    }

    /**
     * Allows user to cancel login and return to the registration screen.
     */
    public void handleBackButtonPressed(ActionEvent actionEvent) {
        mainApp.showLogin();
    }
}
