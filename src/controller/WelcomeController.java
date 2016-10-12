package controller;

import exception.DataBackendException;
import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import model.User;
import model.AccountType;
import model.UserFactory;
import model.DataSource;
import exception.InvalidUserException;

/**
 * Handles events from the welcome screen
 */
public class WelcomeController implements IMainAppReceiver {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<AccountType> accountTypeBox;
    @FXML
    private Label Welcome;

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
     * Displays the login screen when user presses the login button.
     */
    public void login(ActionEvent event) throws Exception {
        mainApp.showLogin();
    }

    /**
     * Attempts to create a new user with the information provided.
     *
     * Displays an error box if user already exists or username/password do not
     * match requirements.
     */
    public void register(ActionEvent actionEvent) {
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
}
