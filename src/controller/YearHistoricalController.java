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

    private static String buttonLabel;
    private MainController mainController;
    private PurityReport report;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        //MainFXApplication mainApp1 = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setReport(PurityReport report) {
        this.report = report;
    }

    @FXML
    protected void initialize() {
        year.getItems().setAll(Year.values());
    }

    @FXML
    public void handleRadioButtonPressed() {
        if (virus.isSelected()) {
            //buttonLabel = "Virus";
            setButtonLabel("Virus");
        } else if (contaminant.isSelected()) {
            //buttonLabel = "Contaminant";
            setButtonLabel("Contaminant");
        }
    }
    /*
     * setter method for button label to remove Dodgy errors
     */
    private static void setButtonLabel(String buttonValue) {
        buttonLabel = buttonValue;
    }

    @FXML
    public void handleViewPressed() {
        mainController.showHistoricalReport(year.getValue(), report);
    }

    @FXML
    public void handleCancelPressed() {
        mainController.showMap();
    }

    public static String getButtonLabel() {
        return buttonLabel;
    }
}
