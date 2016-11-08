package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.PurityReport;
import model.WaterPurityCondition;

/**
 * Handles events sent by the purity reports list.
 */
public class PurityReportListController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private TableView<PurityReport> purityReportTable;
    @FXML
    private TableColumn<PurityReport, String> idCol;
    @FXML
    private TableColumn<PurityReport, String> dateCol;
    @FXML
    private TableColumn<PurityReport, String> creatorCol;
    @FXML
    private TableColumn<PurityReport, Double> latitudeCol;
    @FXML
    private TableColumn<PurityReport, Double> longitudeCol;
    @FXML
    private TableColumn<PurityReport, Double> virusPPMCol;
    @FXML
    private TableColumn<PurityReport, Double> contaminantPPMCol;
    @FXML
    private TableColumn<PurityReport, WaterPurityCondition> conditionCol;
    @FXML
    private TableColumn<PurityReport, String> descriptionCol;

    private MainFXApplication mainApp;
    private MainController mainController;

    /**
     * Configures the columns of the table of reports.
     */
    @FXML
    private void initialize() {
        // Set column cell factories
        idCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getReportNumber()));
        dateCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getDateFormat()));
        creatorCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getCreator().getUser()));
        latitudeCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getLatitude()));
        longitudeCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getLongitude()));
        virusPPMCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getVirusPPM()));
        contaminantPPMCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getContaminantPPM()));
        conditionCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getCondition()));
        descriptionCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getDescription()));
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        mainApp.getDataSource().listPurityReports(
            // Success
            purityReports -> {
                // Populate table
                purityReportTable.getItems().setAll(purityReports);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );
    }

    /**
     * Pressing the graph button takes the user to a dialog which asks
     * the user for appropriate fields. If the user does not select a
     * report first an alert will be shown.
     */
    public void handleGraphButtonPressed() {
        PurityReport report = purityReportTable.getSelectionModel().getSelectedItem();

        if (report == null) {
            mainApp.showAlert("Please select a purity report first.");
        } else {
            mainController.showYearHistoricalView(report);
        }
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
