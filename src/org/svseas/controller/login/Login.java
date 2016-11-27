package org.svseas.controller.login;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.LinkedList;
import java.util.List;


/**
 * Codes by Seong Chee Ken on 26/11/2016, 17:17.
 */
public class Login {
    @FXML
    Label lblFreight;
    @FXML
    Label lblMS;
    @FXML
    JFXButton btnLogin;

    public Login(){
    }

    @FXML
    public void initialize(){
        Font.loadFont(getClass().getResourceAsStream("/font/Aaargh.ttf"), 25);
        List<Label> labelList = new LinkedList<>();
        labelList.add(lblFreight);
        labelList.add(lblMS);

        for (Label label: labelList){
            label.setStyle("-fx-font-family: Aaargh Normal");
        }
    }
}
