package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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

    @FXML
    private StackPane mainPane;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        drawer.setSidePane(loadView("DrawerContent"));
        showMap();
    }

    /**
     * Expands/retracts the drawer when user presses the hamburger.
     */
    @FXML
    private void handleHamburgerClicked(MouseEvent event) {
        if (drawer.isShown()) {
            drawer.close();
            drawer.setOnDrawerClosed((event1) -> drawer.toBack());
        } else {
            drawer.open();
            drawer.setOnDrawerOpening((event1) -> drawer.toFront());
        }
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
     * @param event event raised
     */
    @FXML
    private void handleEditProfile(ActionEvent event) {
        mainApp.editProfile();
    }

    /**
     * Slides the drawer shut.
     */
    public void closeDrawer() {
        drawer.close();
    }

    /**
     * Shows the map screen.
     */
    public void showMap() {
        showView("Map");
    }

    /**
     * Shows the report list screen.
     */
    public void showReportList() {
        showView("ReportList");
    }

    /**
     * Shows the create report screen.
     */
    public void showCreateReport() {
        showView("CreateReport");
    }

    /**
     * Shows the edit profile screen.
     */
    public void showEditProfile() {
        showView("Profile");
    }

    /**
     * Loads the root node of another view, and pass the controller a reference
     * to this instance.
     */
    private Parent loadView(String name) {
        return mainApp.loadView(name, c -> {
            IMainControllerReceiver controller = (IMainControllerReceiver) c;
            controller.setMainController(this);
        });
    }

    /**
     * Loads view and places it in the center of the screen.
     */
    private void showView(String name) {
        mainPane.getChildren().setAll(loadView(name));
    }
}
