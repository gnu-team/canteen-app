package controller;

import javafx.MainFXApplication;

/**
 * Created by jitaekim on 9/18/16.
 */
public class MainController {
    private MainFXApplication mainApplication;

    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }
    private void handleCloseMenu() {
        mainApplication.close();
    }
}
