package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * Handles events from the main screen
 */
public class MainController implements MainAppReceiver {
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

    @FXML
    private void initialize() {
        drawer.setOnDrawerClosed(e -> drawer.toBack());
        drawer.setOnDrawerOpening(e -> drawer.toFront());
    }

    /**
     * Expands/retracts the drawer when user presses the hamburger.
     */
    @FXML
    private void handleHamburgerClicked(MouseEvent event) {
        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
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
     * Shows the purity report list screen.
     */
    public void showPurityReportList() {
        showView("PurityReportList");
    }

    /**
     * Shows the purity report editor.
     */
    public void showCreatePurityReport() {
        showView("CreatePurityReport");
    }

    /**
     * Shows the edit profile screen.
     */
    public void showEditProfile() {
        showView("Profile");
    }

    /**
     * Shows the HistoricalReportView
     */
    public void showHistoricalReport() {
        showView("HistoricalReport");
    }

    /**
     * Loads the root node of another view, and pass the controller a reference
     * to this instance.
     */
    private Parent loadView(String name) {
        return mainApp.loadView(name, c -> {
            MainControllerReceiver controller = (MainControllerReceiver) c;
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
