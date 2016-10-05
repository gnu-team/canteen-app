package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Handles events from the create report screen
 */
public class CreateReportController implements IMainAppReceiver {
    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * When user pressed cancel water report, displays main screen.
     */
    @FXML
    private void handleCancelWaterReport(ActionEvent event) { mainApp.cancelWaterReport(); }
}
