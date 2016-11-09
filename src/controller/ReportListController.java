package controller;

import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Report;
import model.WaterCondition;
import model.WaterType;

/**
 * Handles events sent by the reports list.
 */
public class ReportListController implements MainAppReceiver, MainControllerReceiver {
    @FXML
    private TableView<Report> reportTable;
    @FXML
    private TableColumn<Report, String> idCol;
    @FXML
    private TableColumn<Report, String> dateCol;
    @FXML
    private TableColumn<Report, String> creatorCol;
    @FXML
    private TableColumn<Report, Double> latitudeCol;
    @FXML
    private TableColumn<Report, Double> longitudeCol;
    @FXML
    private TableColumn<Report, WaterType> typeCol;
    @FXML
    private TableColumn<Report, WaterCondition> conditionCol;
    @FXML
    private TableColumn<Report, String> descriptionCol;

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
        typeCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getType()));
        conditionCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getCondition()));
        descriptionCol.cellValueFactoryProperty().setValue(cdf ->
            new ReadOnlyObjectWrapper<>(cdf.getValue().getDescription()));
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {

        mainApp.getDataSource().listReports(
            // Success
            reports -> {
                // Populate table
                reportTable.getItems().setAll(reports);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );
    }

    @Override
    public void setMainController(MainController mainController) {
    }
}
