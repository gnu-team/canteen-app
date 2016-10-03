package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;

public class MainController implements IMainAppReceiver {
    private MainFXApplication mainApp;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    private void handleCloseMenu() {
        mainApp.close();
    }
}
