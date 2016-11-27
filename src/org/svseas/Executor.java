package org.svseas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Codes by Seong Chee Ken on 14/11/2016, 23:03.
 */
public class Executor extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/svseas/fxml/Login.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("blah");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
