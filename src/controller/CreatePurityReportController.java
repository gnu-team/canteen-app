package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.PurityReport;
import model.WaterPurityCondition;

/**
 * Handles events from the create report screen
 */
public class CreatePurityReportController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField virusPPMField;
    @FXML
    private TextField contaminantPPMField;
    @FXML
    private ComboBox<WaterPurityCondition> conditionBox;
    @FXML
    private TextField descriptionField;

    private MainFXApplication mainApp;
    private MainController mainController;

    /**
     * Populates the water purity type combobox.
     */
    @FXML
    private void initialize() {
        conditionBox.getItems().setAll(WaterPurityCondition.values());
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
        Double virusPPM = tryParseDouble(virusPPMField.getText());
        Double contaminantPPM = tryParseDouble(contaminantPPMField.getText());

        if (latitudeField.getText().trim().equals("")
                || longitudeField.getText().trim().equals("")) {
            mainApp.showAlert("Please enter a location");
        } else if (latitude == null) {
            mainApp.showAlert("Please enter a valid number for latitude");
        } else if (longitude == null) {
            mainApp.showAlert("Please enter a valid number for longitude");
        } else if (virusPPM == null) {
            mainApp.showAlert("Please enter a valid number for virus PPM");
        } else if (contaminantPPM == null) {
            mainApp.showAlert("Please enter a valid number for contaminant PPM");
        } else if (conditionBox.getValue() == null) {
            mainApp.showAlert("Please choose a water type");
        } else {
            PurityReport newReport = new PurityReport(
                mainApp.getUser(),
                latitude,
                longitude,
                virusPPM,
                contaminantPPM,
                conditionBox.getValue(),
                descriptionField.getText());

            mainApp.getDataSource().addPurityReport(
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

    /**
     * Shows main screen when user presses cancel report button.
     */
    @FXML
    private void handleCancelReportPressed(ActionEvent event) {
        mainController.showMap();
    }

    private Double tryParseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
