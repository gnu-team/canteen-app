package controller;

/**
 * Handles clicked buttons in the drawer.
 */
public class DrawerContentController implements IMainControllerReceiver {
    private MainController mainController;

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
