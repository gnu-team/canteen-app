package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;

/**
 * Handles clicked buttons in the drawer.
 */
public class DrawerContentController implements MainAppReceiver, MainControllerReceiver {
    private MainFXApplication mainApp;
    private MainController mainController;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Shows map when user presses map button.
     */
    @FXML
    private void handleMapPressed(ActionEvent event) {
        mainController.showMap();
        mainController.closeDrawer();
    }

    /**
     * Shows report list when user presses report list button.
     */
    @FXML
    private void handleReportListPressed(ActionEvent event) {
        mainController.showReportList();
        mainController.closeDrawer();
    }

    /**
     * Shows create report screen when user presses create report button.
     */
    @FXML
    private void handleCreateReportPressed(ActionEvent event) {
        mainController.showCreateReport();
        mainController.closeDrawer();
    }

    /**
     * Shows profile editor when user presses edit profile button.
     */
    @FXML
    private void handleEditProfilePressed(ActionEvent event) {
        mainController.showEditProfile();
        mainController.closeDrawer();
    }
}
