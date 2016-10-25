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
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Hides the node n.
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

    @FXML
    private void handlePurityReportListPressed(ActionEvent event) {
        mainController.showPurityReportList();
        mainController.closeDrawer();
    }

    @FXML
    private void handleCreatePurityReportPressed(ActionEvent event) {
        mainController.showCreatePurityReport();
        mainController.closeDrawer();
    }
}
