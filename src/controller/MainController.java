package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.util.function.Consumer;
import model.PurityReport;
import model.Year;

/**
 * Handles events from the main screen
 */
public class MainController implements MainAppReceiver {
    private MainFXApplication mainApp;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private StackPane mainPane;

    private HamburgerBackArrowBasicTransition transition;

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        drawer.setSidePane(loadView("DrawerContent", null));
        showMap();
    }

    /**
     * Creates a hamburger that pulls out a drawer when clicked. It also
     * transitions to an arrow when clicked and back when clicked again.
     * This is used as the primary method to navigate the app.
     */
    @FXML
    private void initialize() {

        transitionHelper();
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)-> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            drawer.setOnDrawerClosed(e2 -> drawer.toBack());
            drawer.setOnDrawerOpening(e2 -> drawer.toFront());

            if (drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

    }

    /**
     * A private helper method to allow constant use of the hamburger
     * transition across multiple methods in the class without having
     * to create new instance of the HamburgerBackArrowBasicTransition
     */
    private void transitionHelper() {
        transition = new
                HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
    }

    /**
     * When the logout button is pressed, informs MainFXApp, which will unset
     * the current user and display the registration screen.
     */
    @FXML
    private void handleLogoutPressed() {
        mainApp.logout();
    }

    /**
     * Slides the drawer shut.
     */
    private void closeDrawer() {
        transition.setRate(-1);
        transition.play();
        drawer.close();
    }

    /**
     * Shows the map screen.
     */
    public void showMap() {
        showView("Map");
        closeDrawer();
    }

    /**
     * Shows the report list screen.
     */
    public void showReportList() {
        showView("ReportList");
        closeDrawer();
    }

    /**
     * Shows the create report screen.
     */
    public void showCreateReport() {
        showView("CreateReport");
        closeDrawer();
    }

    /**
     * Shows the purity report list screen.
     */
    public void showPurityReportList() {
        showView("PurityReportList");
        closeDrawer();
    }

    /**
     * Shows the purity report editor.
     */
    public void showCreatePurityReport() {
        showView("CreatePurityReport");
        closeDrawer();
    }

    /**
     * Shows the edit profile screen.
     */
    public void showEditProfile() {
        showView("Profile");
        closeDrawer();
    }

    /**
     * Shows the HistoricalReportView
     *
     * @param virus true if Virus PPM should be graphed, false if contaminant
     *              PPM should be graphed
     * @param year The year of reports to graph
     * @param report the report around which to search
     */
    public void showHistoricalReport(boolean virus, Year year, PurityReport report) {
        showView("HistoricalReport", c -> {
            // XXX Don't hardcode the name of the controller like this
            ((HistoricalReportController) c).drawGraphFor(virus, year, report);
        });
        closeDrawer();
    }

    /**
     * Shows the dialoge for the user to choose which year and
     * ppm to graph
     * @param report the report to graph
     */
    public void showYearHistoricalView(PurityReport report) {
        showView("YearHistorical", c -> {
            // XXX Don't hardcode the name of the controller like this
            ((YearHistoricalController) c).setReport(report);
        });
        closeDrawer();
    }

    /**
     * Loads the root node of another view, pass the controller a reference
     * to this instance, and allow controllerConsumer to perform custom
     * operations on the controller.
     */
    private Parent loadView(String name, Consumer<Object> controllerConsumer) {
        return mainApp.loadView(name, c -> {
            MainControllerReceiver controller = (MainControllerReceiver) c;
            controller.setMainController(this);

            if (controllerConsumer != null) {
                controllerConsumer.accept(c);
            }
        });
    }

    /**
     * Loads view and places it in the center of the screen, allowing
     * controllerConsumer to perform custom operations on it if necessary.
     */
    private void showView(String name, Consumer<Object> controllerConsumer) {
        mainPane.getChildren().setAll(loadView(name, controllerConsumer));
    }

    /**
     * Loads view and places it in the center of the screen.
     */
    private void showView(String name) {
        mainPane.getChildren().setAll(loadView(name, null));
    }
}
