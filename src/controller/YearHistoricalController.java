package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import model.PurityReport;
import model.Year;


/**
 * Dialog window for the user to select what kind of ppm
 * and which year to graph.
 */
public class YearHistoricalController implements MainAppReceiver, MainControllerReceiver {

    @FXML
    private RadioButton virus;

    @FXML
    private RadioButton contaminant;

    @FXML
    private ComboBox<Year> year;

    private MainFXApplication mainApp;
    private MainController mainController;
    private PurityReport report;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the report to graph.
     * @param report the report to graph
     */
    public void setReport(PurityReport report) {
        this.report = report;
    }

    /**
     * Sets the year
     */
    @FXML
    private void initialize() {
        year.getItems().setAll(Year.values());
    }

    /**
     * Shows the graph with the selected ppm and year.
     */
    @FXML
    public void handleViewPressed() {
        if (!virus.isSelected() && !contaminant.isSelected()) {
            mainApp.showAlert("Please select a PPM to graph.");
        } else if (year.getValue() == null) {
            mainApp.showAlert("Please select a year to graph.");
        } else {
            mainController.showHistoricalReport(virus.isSelected(), year.getValue(), report);
        }
    }

    /**
     * Goes back to the previous page.
     */
    @FXML
    public void handleCancelPressed() {
        mainController.showMap();
    }
}
