package controller;

import javafx.IMainAppReceiver;
import javafx.MainFXApplication;

/**
 * Created by jitaekim on 9/18/16.
 */
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
