package org.svseas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.svseas.utils.Initializer;

/**
 * Coded by Seong Chee Ken on 14/11/2016, 23:03.
 */
public class Executor extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Initializer();

        Parent root = FXMLLoader.load(getClass().getResource("/org/svseas/view/PortManagement.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("7SEAS Freight Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
