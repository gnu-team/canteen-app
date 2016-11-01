package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
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

    public static String buttonLabel;
    private MainFXApplication mainApp;
    private MainController mainController;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        year.getItems().setAll(Year.values());
        if (virus.isPressed()) {
            buttonLabel = "Virus";
        } else {
            buttonLabel = "Contaminant";
        }
    }

    @FXML
    public void handleViewPressed() {
        mainController.showHistoricalReport();
    }

    @FXML
    public void handleCancelPressed() {
        mainController.showMap();
    }

    //private void getRadioValue() {
    //    if (virus.isPressed()) {
    //        buttonLabel = "Virus";
    //    } else {
    //        buttonLabel = "Contaminant";
    //    }
    //}
}
