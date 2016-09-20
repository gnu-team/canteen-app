package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.User;


public class WelcomeController {
    @FXML
    private TextField UserName;

    @FXML
    private TextField PassWord;

    @FXML
    private Label Welcome;
    private User user;

    public void Login(ActionEvent event) throws Exception {
        if ((UserName.getText().equals(user.getUser())) && PassWord.getText().equals(user.getPassword())
            || ((UserName.getText().equals("user") && PassWord.getText().equals("pass")))) {
            //it hide current window
            ((Node)(event.getSource())).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("../view/WelcomeView.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Success Frame");
            primaryStage.show();
        } else {
            Welcome.setText("Login failed");
        }
    }






















}
