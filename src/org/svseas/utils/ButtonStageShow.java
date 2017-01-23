package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.svseas.utils.manipulator.Manipulator;

import java.io.IOException;

/**
 * Coded by Seong Chee Ken on 21/01/2017, 16:51. Any button operations which function as
 * stage shower shall use this class.
 */
public class ButtonStageShow {
    private JFXButton button;
    private String url;
    private String stageTitle;

    public ButtonStageShow(JFXButton button, String url, String stageTitle) {
        this.button = button;
        this.url = url;
        this.stageTitle = stageTitle;
    }

    public ButtonStageShow(String url, String stageTitle) {
        this.url = url;
        this.stageTitle = stageTitle;
    }

    public void operate(){
        button.setOnMouseClicked( event -> {
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource(url));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle(stageTitle);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //pass parameter to edit stage

    /**
     * Must use as {@code instance.<T,C>operate(T param)!}
     */
    public <T, C extends Manipulator> void operate(T param){
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(stageTitle);
            C controller = loader.getController();
            controller.initData(param);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
