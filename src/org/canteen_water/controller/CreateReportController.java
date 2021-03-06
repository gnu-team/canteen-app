package org.canteen_water.controller;

import org.canteen_water.javafx.MainAppReceiver;
import org.canteen_water.javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.canteen_water.model.Report;
import org.canteen_water.model.WaterCondition;
import org.canteen_water.model.WaterType;

/**
 * Handles events from the create report screen
 */
public class CreateReportController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private ComboBox<WaterType> waterTypeBox;
    @FXML
    private ComboBox<WaterCondition> waterConditionBox;
    @FXML
    private TextField descriptionField;

    private MainFXApplication mainApp;
    private MainController mainController;

    /**
     * Populates the water type combobox.
     */
    @FXML
    private void initialize() {
        waterTypeBox.getItems().setAll(WaterType.values());
        waterConditionBox.getItems().setAll(WaterCondition.values());
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
        Double latitude = tryParseDouble(latitudeField.getText());
        Double longitude = tryParseDouble(longitudeField.getText());

        if (latitudeField.getText().trim().equals("")
                || longitudeField.getText().trim().equals("")) {
            mainApp.showAlert("Please enter a location");
        } else if (latitude == null) {
            mainApp.showAlert("Please enter a valid number for latitude");
        } else if (longitude == null) {
            mainApp.showAlert("Please enter a valid number for longitude");
        } else if (waterTypeBox.getValue() == null) {
            mainApp.showAlert("Please choose a water type");
        } else if (waterConditionBox.getValue() == null) {
            mainApp.showAlert("Please choose a water condition");
        } else {
            Report newReport = new Report(
                mainApp.getUser(),
                latitude,
                longitude,
                waterTypeBox.getValue(),
                waterConditionBox.getValue(),
                descriptionField.getText());

            mainApp.getDataSource().addReport(
                newReport,
                // Success
                () -> {
                    mainController.showMap();
                },
                // Failure
                e -> {
                    e.printStackTrace();
                    mainApp.showAlert(e.getMessage());
                }
            );
        }
    }

    private Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
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
