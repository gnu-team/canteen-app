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

    private static String buttonLabel;
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
    }

    @FXML
    public void handleRadioButtonPressed() {
        if (virus.isSelected()) {
            buttonLabel = "Virus";
        } else if (contaminant.isSelected()) {
            buttonLabel = "Contaminant";
        }
    }

    @FXML
    public void handleViewPressed() {
        mainController.showHistoricalReport(0, 0, null);
    }

    @FXML
    public void handleCancelPressed() {
        mainController.showMap();
    }

    public static String getButtonLabel() {
        return buttonLabel;
    }
}
