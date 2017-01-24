package org.svseas.controller.route;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.svseas.utils.manipulator.RouteManipulator;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:43.
 */
public class RouteAdd extends RouteManipulator {
    @FXML private StackPane routeadd_root;
    @FXML private JFXTextField route_id, charge_per_nm, route_name;
    @FXML private JFXComboBox<String> cbox_source, cbox_destination;
    @FXML private JFXButton btn_add, btn_reset;
    @FXML private Label label_noPorts, label_intermediate, label_dist, label_length, label_total;

    @FXML
    public void initialize(){

    }

    @Override
    public <T> void initData(T param) {}
}
