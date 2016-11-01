package controller;


import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;

import static controller.YearHistoricalController.buttonLabel;

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



        lineChart.setTitle(YearHistoricalController.buttonLabel + " PPM");



        series = new XYChart.Series();
        series.setName("My portfolio");

        lineChart.getData().add(series);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
        series.getData().add(new XYChart.Data<String, Number>("Jan", 23));
        series.getData().add(new XYChart.Data<String, Number>("Feb", 28));
        //lineChart.getData().add(series);
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
