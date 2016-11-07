package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import model.PurityReport;
import model.Year;


/**
 * Created by Ph3ncyclidine on 10/31/16.
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

    public void setReport(PurityReport report) {
        this.report = report;
    }

    @FXML
    private void initialize() {
        year.getItems().setAll(Year.values());
    }

    @FXML
    public void handleViewPressed() {
        mainController.showHistoricalReport(virus.isSelected(), year.getValue(), report);
    }

    @FXML
    public void handleCancelPressed() {
        mainController.showMap();
    }
}
