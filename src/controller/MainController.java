package controller;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.IMainAppReceiver;
import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

/**
 * Handles events from the main screen
 */
public class MainController implements IMainAppReceiver {
    private MainFXApplication mainApp;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;


    @Override
    public void setMainApp(MainFXApplication mainApp) {
        this.mainApp = mainApp;
        /**
         * It set a view for navigation Drawer content
         * it controls hamburger button
         */
        drawer.setSidePane(mainApp.loadView("DrawerContent"));
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isShown())
            {
                drawer.close();
            }else
                drawer.open();
        });
    }

    /**
     * When the logout button is pressed, informs MainFXApp, which will unset
     * the current user and display the registration screen.
     */
    @FXML
    private void handleLogoutPressed(ActionEvent event) {
        mainApp.logout();
    }

    /**
     * When user pressed edit profile button, displays edit profile screen.
     */
    @FXML
    private void handleEditProfile(ActionEvent event) {
        mainApp.editProfile();
    }

}
