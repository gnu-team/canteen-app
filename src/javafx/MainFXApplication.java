package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.util.function.Consumer;

import model.ApiDataSource;
import model.DataSource;
import model.User;

import java.io.IOException;
import java.net.URL;

/**
 * Main program which loads views as instructed by controllers.
 */
public class MainFXApplication extends Application {
    // TODO: Put this in a ResourceBundle instead
    private static final String TITLE = "Canteen: A Water Tracker";

    private User user;
    private Stage stage;
    private DataSource dataSource;

    /**
     * Sets the window title and displays the registration screen.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        // For now, store system state in memory
        dataSource = new ApiDataSource();

        primaryStage.setTitle(TITLE);
        showLogin();
        primaryStage.show();
    }

    /**
     * Return the DataSource used to store state in this application.
     * @return A DataSource instance
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Load a view and return its root node.
     *
     * @param name name of the view to load
     * @return root node of view specified
     */
    public Parent loadView(String name) {
        return loadView(name, null);
    }

    /**
     * Load a view and return its root node.
     *
     * @param name name of the view to load
     * @param controllerConsumer function performing custom operations on
     *        controller, or null
     * @return root node of view specified
     */
    public Parent loadView(String name, Consumer<Object> controllerConsumer) {
        URL path = getClass().getResource("/view/" + name + "View.fxml");

        // If getResource() does not find the resource given, it returns
        // null
        if (path == null) {
            bail("Cannot locate view " + name + ".");
            return null;
        }

        FXMLLoader loader = new FXMLLoader(path);
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            bail("IOException thrown when loading view '" + name + "'.");
            return null;
        }

        // TODO: Use dependency injection or something instead of this
        // interface-cast hack
        MainAppReceiver controller = (MainAppReceiver) loader.getController();
        if (controller != null) {
            controller.setMainApp(this);

            // Do caller-specific stuff on controller
            if (controllerConsumer != null) {
                controllerConsumer.accept(controller);
            }
        }

        return root;
    }

    /**
     * Load a view and place it in the main window.
     *
     * @param name name of the view to load
     */
    private void showView(String name) {
        Parent root = loadView(name);

        if (root != null) {
            stage.setScene(new Scene(root));
        }
    }

    /**
     * Show the user a error box.
     *
     * Used, for example, when user enters an incorrect password.
     *
     * @param message Message shown to user
     */
    public void showAlert(String message) {
        Alert alert = new WarningAlert(message);
        alert.showAndWait();
    }

    /**
     * Display an error box and exit.
     *
     * @param message Error message to show. Followed by "Bailing out!" on a new
     *                line.
     */
    private void bail(String message) {
        showAlert(message + "\nBailing out!");
        close();
    }

    /**
     * Ends the application.
     * See: http://stackoverflow.com/a/12154135/321301
     */
    public void close() {
        stage.close();
    }

    /**
     * Returns current user.
     *
     * @return The current User if authenticated, else null.
     */
    public User getUser() {
        return user;
    }

    /**
     * Stores the account used to authenticate after login has succeeded
     * @param user Account used to authenticate
     */
    public void loginComplete(User user) {
        this.user = user;
        showView("Main");
    }

    /**
     * Displays welcome screen.
     */
    public void showRegister() {
        showView("Register");
    }

    /**
     * Displays login screen.
     */
    public void showLogin() {
        showView("Login");
    }

    /**
     * Unsets the current user and show registration screen again.
     */
    public void logout() {
        this.user = null;
        showLogin();
    }

    /**
     * After registration succeeds, sets current user and displays the profile
     * editor.
     *
     * @param user New user to set as current user. Should already be added to
     *             DataSource.
     */
    public void finishRegistration(User user) {
        this.user = user;
        editProfile();
    }

    /**
     * Displays edit profile screen for authenticated users.
     */
    public void editProfile() {
        showView("Profile");
    }

    /**
     * Once profile is completed, shows success screen.
     */
    public void registrationComplete() {
        showView("Main");
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
