package controller;

import model.exception.DataException;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.PurityReport;
import model.WaterPurityCondition;

import java.util.Collection;

/**
 * Handles events sent by the purity reports list.
 */
public class PurityReportListController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private TableView<PurityReport> purityReportTable;
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

        // Populate table
        Collection<PurityReport> purityReports;
        try {
            purityReports = mainApp.getDataSource().listPurityReports();
        } catch (DataException e) {
            e.printStackTrace();
            mainApp.showAlert(e.getMessage());
            return;
        }
        purityReportTable.getItems().setAll(purityReports);
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
