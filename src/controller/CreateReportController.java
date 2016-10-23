package controller;

import model.exception.DataException;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.*;

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
        Double latitude, longitude;

        if (latitudeField.getText().trim().equals("")
                || longitudeField.getText().trim().equals("")) {
            mainApp.showAlert("Please enter a location");
        } else if ((latitude = tryParseDouble(latitudeField.getText())) == null) {
            mainApp.showAlert("Please enter a valid number for latitude");
        } else if ((longitude = tryParseDouble(longitudeField.getText())) == null) {
            mainApp.showAlert("Please enter a valid number for longitude");
        } else if (waterTypeBox.getValue() == null) {
            mainApp.showAlert("Please choose a water type");
        } else if (waterConditionBox.getValue() == null) {
            mainApp.showAlert("Please choose a water condition");
        } else {
            try {
                mainApp.getDataSource().addReport(new Report(
                    mainApp.getUser(),
                    latitude,
                    longitude,
                    waterTypeBox.getValue(),
                    waterConditionBox.getValue(),
                    descriptionField.getText()));
            } catch (DataException be) {
                be.printStackTrace();
                mainApp.showAlert(be.getMessage());
                return;
            }

            mainController.showMap();
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
