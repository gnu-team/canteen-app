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
import model.Report;
import model.exception.DataException;
import netscape.javascript.JSObject;

import java.util.Collection;

/**
 * Created by Claude Peon on 10/16/16.
 */
public class MapController implements MainAppReceiver, MainControllerReceiver,
                                      MapComponentInitializedListener {
    private static final double GEORGIA_TECH_LAT = 33.779;
    private static final double GEORGIA_TECH_LONG = -84.398;
    private static final int GEORGIA_TECH_ZOOM = 14;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;
    private MainFXApplication mainApp;
    private MainController mainController;
    private Collection<Report> reports;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this);
    }

    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;

        mainApp.getDataSource().listReports(
            // Success
            reports -> {
                this.reports = reports;
                drawReports();
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

    private void drawReports() {
        if (reports == null || map == null) {
            return;
        }

        for (Report r : reports) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(r.getLatitude(), r.getLongitude());

            markerOptions.position(loc)
                    .visible(Boolean.TRUE)
                    .title(r.getSummary());

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(String.format(
                            "<strong>%s</strong><br>%.4f, %.4f<br><br>%s",
                            r.getSummary(), r.getLatitude(), r.getLongitude(),
                            r.getDescription()));

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }

        // Don't need reports anymore, so allow them to get garbage collected.
        // This also marks the reports as drawn.
        reports = null;
    }
}
