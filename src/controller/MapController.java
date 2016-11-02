package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import javafx.MainFXApplication;
import javafx.MainAppReceiver;
import javafx.fxml.FXML;
import model.MapPin;
import model.Report;
import model.PurityReport;
import model.exception.DataException;
import netscape.javascript.JSObject;

import java.util.Collection;

/**
 * Created by Claude Peon on 10/16/16.
 */
public class MapController implements MainAppReceiver, MainControllerReceiver,
                                      MapComponentInitializedListener {
    private static final String ICON_FORMAT = "https://canteen-water.org/static/canteen_browser/img/%s32.png";
    private static final double GEORGIA_TECH_LAT = 33.779;
    private static final double GEORGIA_TECH_LONG = -84.398;
    private static final int GEORGIA_TECH_ZOOM = 14;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;
    private MainFXApplication mainApp;
    private MainController mainController;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        // Set up the center location on Georgia Tech
        LatLong center = new LatLong(GEORGIA_TECH_LAT, GEORGIA_TECH_LONG);

        options.center(center)
                .zoom(GEORGIA_TECH_ZOOM)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);

        drawReports();
    }

    /**
     * Attempts to list and draw points for all reports.
     */
    private void drawReports() {
        mainApp.getDataSource().listReports(
            // Success
            reports -> {
                drawPins(reports);
            },
            // Failure
            e -> {
                e.printStackTrace();
                mainApp.showAlert(e.getMessage());
            }
        );

        if (mainApp.getUser().canUsePurityReports()) {
            mainApp.getDataSource().listPurityReports(
                // Success
                purityReports -> {
                    drawPins(purityReports);
                },
                // Failure
                e -> {
                    e.printStackTrace();
                    mainApp.showAlert(e.getMessage());
                }
            );
        }
    }

    /**
     * Adds pins to the map for a given set of reports.
     */
    private void drawPins(Iterable<? extends MapPin> pins) {
        for (MapPin p : pins) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(p.getLatitude(), p.getLongitude());

            markerOptions.position(loc)
                    .visible(Boolean.TRUE)
                    .icon(String.format(ICON_FORMAT, p.getColor()))
                    .title(p.getSummary());

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(String.format(
                            "<strong>%s</strong><br>%.4f, %.4f<br><br>%s",
                            p.getSummary(), p.getLatitude(), p.getLongitude(),
                            p.getDescription()));

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }
    }
}
