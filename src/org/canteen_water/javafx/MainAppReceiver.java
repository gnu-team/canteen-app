package org.canteen_water.javafx;

// TODO: Use dependency injection or something instead of this
// interface-cast hack

/**
 * To allow controllers to communicate with MainFXApplication, pass them
 * a reference to it.
 *
 * All controllers must implement this interface.
 */
public interface MainAppReceiver {
    /**
     * Passes a reference to the running MainFXApplication
     * instance into this controller.
     *
     * Controllers use this reference to show alerts, get/set the current user,
     * and swap screens.
     *
     * @param mainApp A reference to the running MainFXApplication.
     */
    void setMainApp(MainFXApplication mainApp);
}
