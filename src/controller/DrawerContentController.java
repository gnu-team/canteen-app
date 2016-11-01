package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;

/**
 * Handles clicked buttons in the drawer.
 */
public class DrawerContentController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private Button purityReportListButton;
    @FXML
    private Button createPurityReportButton;
    @FXML
    private Button historyReportButton;

    private MainFXApplication mainApp;
    private MainController mainController;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        // Hide purity report controls if needed
        if (!mainApp.getUser().canUsePurityReports()) {
            hide(purityReportListButton);
            hide(createPurityReportButton);
        }
        // Hide history report control if less than a Manager
        if (!mainApp.getUser().canViewHistoryReports()) {
            hide(historyReportButton);
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
    private void handleMapPressed(ActionEvent event) {
        mainController.showMap();
    }

    /**
     * Shows report list when user presses report list button.
     */
    @FXML
    private void handleReportListPressed(ActionEvent event) {
        mainController.showReportList();
    }

    /**
     * Shows create report screen when user presses create report button.
     */
    @FXML
    private void handleCreateReportPressed(ActionEvent event) {
        mainController.showCreateReport();
    }

    /**
     * Shows profile editor when user presses edit profile button.
     */
    @FXML
    private void handleEditProfilePressed(ActionEvent event) {
        mainController.showEditProfile();
    }

    @FXML
    private void handlePurityReportListPressed(ActionEvent event) {
        mainController.showPurityReportList();
    }

    @FXML
    private void handleCreatePurityReportPressed(ActionEvent event) {
        mainController.showCreatePurityReport();
    }

    @FXML
    private void handleHistoricalReportPressed(ActionEvent event) {
        mainController.showHistoricalReport(0, 0, null);
    }
}
