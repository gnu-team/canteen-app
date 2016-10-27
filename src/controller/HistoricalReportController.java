package controller;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;

/**
 * Created by Ph3ncyclidine on 10/26/16.
 */
public class HistoricalReportController implements MainAppReceiver, MainControllerReceiver {
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
}
