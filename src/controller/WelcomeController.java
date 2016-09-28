package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import model.User;
import model.AccountType;


public class WelcomeController implements IMainAppReceiver {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<AccountType> accountTypeBox;

    @FXML
    private Label Welcome;
    private User user = User.getDefaultUser();
    private MainFXApplication mainApplication;

    @FXML
    private void initialize() {
        accountTypeBox.getItems().setAll(AccountType.values());
    }

    public void setMainApp(MainFXApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void login(ActionEvent event) throws Exception {
        mainApplication.showLogin();
    }

    public void register(ActionEvent actionEvent) {
        mainApplication.showAlert("Registration is not implemented yet.");
    }
}
