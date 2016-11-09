package controller;


import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.MainAppReceiver;
import javafx.MainFXApplication;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.PurityReport;
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

        lineChart.setTitle(YearHistoricalController.getButtonLabel() + " PPM");

        series = new XYChart.Series();

        lineChart.getData().add(series);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }


    @Override
    public void setMainController(MainController mainController) {
        //this.mainController = mainController;
    }

    public void drawGraphFor(Year year, PurityReport report) {
        // Set series name
        series.setName("Reports near " + report.getLatitude() + "," + report.getLongitude());

        mainApp.getDataSource().listNearbyPurityReports(
            year, report,
            // Success
            reports -> {
                drawPoints(reports);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
                //return;
            }
        );
    }

    private int reportMonth(PurityReport report) {
        Calendar c = Calendar.getInstance();
        c.setTime(report.getDate());
        return c.get(Calendar.MONTH);
    }

    private void drawPoints(Collection<PurityReport> reports) {
        // First, sort reports by date
        List<PurityReport> sorted = new ArrayList<>(reports);
        Collections.sort(sorted, (r1, r2) -> r1.getDate().compareTo(r2.getDate()));

        for (int i = 0; i < sorted.size();) {
            // Index of next report with unique month
            int j;

            for (j = i + 1; j < sorted.size() &&
                 reportMonth(sorted.get(j)) == reportMonth(sorted.get(i)); j++);

            double avg = 0;
            // Now, find sum
            for (int k = i; k < j; k++) {
                // XXX Show contaminant PPM instead if chosen in
                //     YearHistoricalC8r
                avg += sorted.get(k).getVirusPPM();
            }
            avg /= j - i;

            drawPoint(reportMonth(sorted.get(i)), avg);

            i = j;
        }
    }

    private void drawPoint(int month, double avg) {
        String monthName = new DateFormatSymbols().getMonths()[month];
        series.getData().add(new XYChart.Data<String, Number>(monthName, avg));
    }
}
