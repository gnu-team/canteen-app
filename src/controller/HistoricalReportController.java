package controller;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import javafx.stage.Stage;
import model.Year;

/**
 * Created by Ph3ncyclidine on 10/26/16.
 */
public class HistoricalReportController implements MainAppReceiver, MainControllerReceiver {

    @FXML
    private LineChart<String, Number> lineChart;
    private XYChart.Series series;
    private MainFXApplication mainApp;
    private MainController mainController;

    public void initialize() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("PPM");
        //lineChart = new LineChart<>(xAxis, yAxis);



        lineChart.setTitle(YearHistoricalController.getButtonLabel() + " PPM");



        series = new XYChart.Series();
        series.setName("My portfolio");

        lineChart.getData().add(series);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
        series.getData().add(new XYChart.Data<String, Number>("Jan", 23));
        //lineChart.getData().add(series);
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void drawGraphFor(double latitude, double longitude, Year year) {
        mainApp.getDataSource().listNearbyPurityReports(
            latitude, longitude,
            // Success
            reports -> {
                // TODO: Draw chart with reports
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
                return;
            }
        );
    }
}
