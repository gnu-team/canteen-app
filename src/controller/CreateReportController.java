package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.util.Date;

/**
 * Handles events from the create report screen
 */
public class CreateReportController implements IMainAppReceiver, IMainControllerReceiver {
    @FXML
    private TextField locationField;
    @FXML
    private ComboBox<WaterType> waterTypeBox;
    @FXML
    private ComboBox<WaterCondition> waterConditionBox;
    private MainFXApplication mainApp;
    private MainController mainController;

    private static int reportNumber;
    /**
     * Populates the water type combobox.
     */
    @FXML
    private void initialize() {
        waterTypeBox.getItems().setAll(WaterType.values());
        waterConditionBox.getItems().setAll(WaterCondition.values());
    }
    /**
     * Each ReportNumber increamented by 1
     */
    public int getReportNumber() {
        return reportNumber++;
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Shows create report screen when user presses create report button.
     */
    @FXML
    private void handleCreateReportPressed(ActionEvent event) {
        if (locationField.getText().trim().equals("")) {
            mainApp.showAlert("Please enter a location");
        } else if (waterTypeBox.getValue() == null) {
            mainApp.showAlert("Please choose a water type");
        } else if (waterConditionBox.getValue() == null) {
            mainApp.showAlert("Please choose a water condition");
        } else {
            mainApp.getDataSource().addReport(new Report(
                mainApp.getUser(),
                locationField.getText(),
                waterTypeBox.getValue(),
                waterConditionBox.getValue()
            ));
            mainController.showMap();
        }
    }

    /**
     * Shows main screen when user presses cancel report button.
     */
    @FXML
    private void handleCancelReportPressed(ActionEvent event) {
        mainController.showMap();
    }
}
