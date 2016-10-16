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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import netscape.javascript.JSObject;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Claude Peon on 10/16/16.
 */
public class MapController implements Initializable, MapComponentInitializedListener {

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private Window mainStage;

    private MainFXApplication app;

    @Override
    public void initialize(URL url, ResourceBundle) {
        mapView.addMapInializedListener(this);
    }

    public void setCallbacks(Window stage, MainFXApplication app) {
        mainStage = stage;
        this.app = app;
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();

        //set up the center location for the map
        LatLong center = new LatLong(33, 84);

        options.center(center)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);

        map = mapView.createMap(options);


        /** now we communciate with the model to get all the locations for markers */
        Facade fc = Facade.getInstance();
        List<Location> locations = fc.getLocations();

        for (Location l: locations) {
            MarkerOptions markerOptions = new MarkerOptions();
            LatLong loc = new LatLong(l.getLatitude(), l.getLongitude());

            markerOptions.position(loc)
                    .visible(Boolean.TRUE)
                    .title(l.getTitle());

            Marker marker = new Marker(markerOptions);

            map.addUIEventHandler(marker,
                    UIEventType.click,
                    (JSObject obj) -> {
                        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                        infoWindowOptions.content(l.getDescription());

                        InfoWindow window = new InfoWindow(infoWindowOptions);
                        window.open(map, marker);
                    });

            map.addMarker(marker);
        }


    }

    @FXML
    public void onOpenTextFileMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Text File");
        File file  = fc.showOpenDialog(mainStage);
        if (file != null)
            Facade.getInstance().loadModelFromText(file);
    }

    @FXML
    public void onOpenBinaryFileMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Binary File");
        File file  = fc.showOpenDialog(mainStage);
        if (file != null)
            Facade.getInstance().loadModelFromBinary(file);
    }

    @FXML
    public void onOpenJsonFileMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open JSON File");
        File file  = fc.showOpenDialog(mainStage);
        if (file != null)
            Facade.getInstance().loadModelFromJson(file);
    }

    @FXML
    public void onSaveTextFileMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Text File");
        File file  = fc.showSaveDialog(mainStage);
        if (file != null)
            Facade.getInstance().saveModelToText(file);
    }

    @FXML
    public void onSaveBinaryFileMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Binary File");
        File file  = fc.showSaveDialog(mainStage);
        if (file != null)
            Facade.getInstance().saveModelToBinary(file);
    }

    @FXML
    public void onSaveJsonMenu() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save JSON File");
        File file  = fc.showSaveDialog(mainStage);
        if (file != null)
            Facade.getInstance().saveModelToJson(file);
    }

    @FXML
    public void onCloseMenu() {
        theApp.closeMapView();
    }
}
