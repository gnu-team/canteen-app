package javafx;

// TODO: Use dependency injection or something instead of this
// interface-cast hack

/**
 * To allow controllers to communicate with MainFXApplication, pass them
 * a reference to it.
 * All controllers must implement this class.
 */
public interface IMainAppReceiver {
    void setMainApp(MainFXApplication mainApp);
}
