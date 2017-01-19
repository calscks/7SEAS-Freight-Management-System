package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.svseas.controller.ParentController;


/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:29.
 */
public class ClientAdd extends ParentController{
    @FXML private StackPane clientadd_root;
    @FXML private JFXButton btn_add;
    @FXML private JFXTextField username, companyName, registry_no, phone_no;
    @FXML private JFXPasswordField pwd, confirm_pwd;

    @FXML
    public void initialize(){

    }
}
