package controller;

// TODO: Use dependency injection or something instead of this
// interface-cast hack

/**
 * To allow controllers to communicate with the main controller, pass
 * them a reference to it.
 *
 * All controllers assigned to views launched by MainController must
 * implement this interface.
 */
public interface IMainControllerReceiver {
    /**
     * Passes a reference to MainController into this controller.
     *
     * Controllers use this reference to show alerts, get/set the current user,
     * and swap screens.
     *
     * @param mainController A reference to the active MainController.
     */
    void setMainController(MainController mainController);
}
