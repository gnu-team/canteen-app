package controller;

import javafx.MainFXApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

/**
 * Created by jitaekim on 9/18/16.
 */
public class JoinController {
    @FXML
    private TextField JoinUserName;
    @FXML
    private TextField JoinPassWord;
    private User user;
    private MainFXApplication mainApplication;


    private void handleLoginPressed(ActionEvent event) {
        if (isInputValid()) {
            user.setUser(JoinPassWord.getText());
            user.setPassword(JoinPassWord.getText());
            try {
                ((Node)(event.getSource())).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../view/LogOut.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Success Frame");
                primaryStage.show();
            } catch (Exception e) {
            }
        }
    }

    private void handleBackPressed(ActionEvent event) {
        try {
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("../view/join.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome Frame");
            primaryStage.show();
        } catch (Exception e) {
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (JoinPassWord.getText() == null || JoinPassWord.getText().length() == 0) {
            errorMessage += "No valid Password";
        }
        if (JoinUserName.getText() == null || JoinUserName.getText().length() == 0) {
            errorMessage += "No valid User name";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            //alert.initOwner(mainApplication.getMainScreen());
            alert.setTitle("Error Message");
            alert.setHeaderText("Invalid UserName or Password");
            alert.setContentText("try again with valid Name or Password");
            alert.showAndWait();
            return false;
        }
    }

































}
