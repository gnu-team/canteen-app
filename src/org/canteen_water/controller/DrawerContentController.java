package org.canteen_water.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.canteen_water.javafx.MainAppReceiver;
import org.canteen_water.javafx.MainFXApplication;

/**
 * Handles clicked buttons in the drawer.
 */
public class DrawerContentController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private Button purityReportListButton;
    @FXML
    private Button createPurityReportButton;

    private MainController mainController;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        // Hide purity report controls if needed
        if (!mainApp.getUser().canUsePurityReports()) {
            hide(purityReportListButton);
            hide(createPurityReportButton);
        }
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the node as managed so that there isn't a blank
     * space, then makes it invisible. This method is used to hide
     * elements of the ui that are only intended for higher level users.
     *
     * @param n node to hide
     */
    private void hide(Node n) {
        n.setManaged(false);
        n.setVisible(false);
    }

    /**
     * Shows map when user presses map button.
     */
    @FXML
    private void handleMapPressed() {
        mainController.showMap();
    }

    /**
     * Shows report list when user presses report list button.
     */
    @FXML
    private void handleReportListPressed() {
        mainController.showReportList();
    }

    /**
     * Shows create report screen when user presses create report button.
     */
    @FXML
    private void handleCreateReportPressed() {
        mainController.showCreateReport();
    }

    /**
     * Shows profile editor when user presses edit profile button.
     */
    @FXML
    private void handleEditProfilePressed() {
        mainController.showEditProfile();
    }

    @FXML
    private void handlePurityReportListPressed() {
        mainController.showPurityReportList();
    }

    @FXML
    private void handleCreatePurityReportPressed() {
        mainController.showCreatePurityReport();
    }
}
