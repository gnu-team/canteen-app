package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;

/**
 * Handles events from the main screen
 */
public class MainController implements IMainAppReceiver {
    private MainFXApplication mainApp;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        drawer.setSidePane(mainApp.loadView("DrawerContent"));
    }

    /**
     * When the logout button is pressed, informs MainFXApp, which will unset
     * the current user and display the registration screen.
     */
    @FXML
    private void handleLogoutPressed(ActionEvent event) {
        mainApp.logout();
    }

    /**
     * When user pressed edit profile button, displays edit profile screen.
     */
    @FXML
    private void handleEditProfile(ActionEvent event) {
        mainApp.editProfile();
    }
}
