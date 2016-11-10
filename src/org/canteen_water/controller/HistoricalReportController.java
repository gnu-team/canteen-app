package org.canteen_water.controller;


import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.canteen_water.javafx.MainAppReceiver;
import org.canteen_water.javafx.MainFXApplication;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.canteen_water.model.PurityReport;
import org.canteen_water.model.Year;

/**
 * Draws a chart for the history of the water's purity.
 */
public class HistoricalReportController implements MainAppReceiver, MainControllerReceiver {

    @FXML
    private LineChart<String, Number> lineChart;
    private XYChart.Series series;
    private MainFXApplication mainApp;

    /**
     * Initializes the graph with startup data
     */
    public void initialize() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("PPM");

        series = new XYChart.Series();
        lineChart.getData().add(series);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
    }

    /**
     * Called by main controller to specify what to graph
     * @param virus true if virus is selected false for contaminate
     * @param year the year we're looking at
     * @param report the report to graph
     */
    public void drawGraphFor(boolean virus, Year year, PurityReport report) {
        // Apply the right PPM label
        lineChart.setTitle((virus ? "Virus" : "Contaminant") + " PPM");
        // Set series name
        series.setName("Reports near " + report.getLatitude() + "," + report.getLongitude());

        mainApp.getDataSource().listNearbyPurityReports(
            year, report,
            // Success
            reports -> {
                drawPoints(virus, reports);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );
    }

    /**
     * Gets the month of the report as an integer
     * @param report the report we want to use
     * @return the month as an integer
     */
    private int reportMonth(PurityReport report) {
        Calendar c = Calendar.getInstance();
        c.setTime(report.getDate());
        return c.get(Calendar.MONTH);
    }

    /**
     * Draws the points on the graph
     * @param virus true if virus is selected, false if contaminate is selected
     * @param reports the report to draw the points of
     */
    private void drawPoints(boolean virus, Collection<PurityReport> reports) {
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
                PurityReport report = sorted.get(k);
                avg += (virus ? report.getVirusPPM()
                              : report.getContaminantPPM());
            }
            avg /= j - i;

            drawPoint(reportMonth(sorted.get(i)), avg);

            i = j;
        }
    }

    /**
     * Draws an individual point
     * @param month the month of the point to be drawn
     * @param avg the average of the months
     */
    private void drawPoint(int month, double avg) {
        String monthName = new DateFormatSymbols().getMonths()[month];
        series.getData().add(new XYChart.Data<String, Number>(monthName, avg));
    }
}
