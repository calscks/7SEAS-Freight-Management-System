package org.svseas.controller.route;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.operations.RouteOperations;
import org.svseas.utils.Dialogue;
import org.svseas.utils.manipulator.RouteManipulator;

import java.util.ArrayList;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:43.
 */
public class RouteAdd extends RouteManipulator {
    @FXML
    private StackPane routeadd_root;
    @FXML
    private JFXTextField route_id, charge_per_nm, route_name;
    @FXML
    private JFXComboBox<String> cbox_source, cbox_destination;
    @FXML
    private JFXButton btn_add, btn_reset;
    @FXML
    private Label label_noPorts, label_dist, label_length, label_total;

    private ArrayList<Port> ports;

    private Route<Port> route;

    @FXML
    public void initialize() {
        BooleanBinding binding = route_id.textProperty().isEmpty()
                .or(charge_per_nm.textProperty().isEmpty())
                .or(route_name.textProperty().isEmpty())
                .or(cbox_source.selectionModelProperty().isNull())
                .or(cbox_destination.selectionModelProperty().isNull());
        btn_add.disableProperty().bind(binding);

        loadPort(cbox_source, cbox_destination);
        ports = numberOfPorts(cbox_source, cbox_destination, label_noPorts, label_length, label_dist);
        totalCharges(label_total, label_dist, charge_per_nm);

        manipulate(btn_add);

    }

    @Override
    public void manipulate(JFXButton btn_add) {
        btn_add.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                ArrayList<String> portName = new ArrayList<>();
                for (Port port : ports){
                    portName.add(port.getName());
                }
                String travel = String.join(" -> ", portName);
                System.out.println(travel);

                route = new Route<>(route_id.getText(), route_name.getText(), label_length.getText(),
                        charge_per_nm.getText(), label_total.getText(), travel, label_dist.getText());
                route.setPortList(ports);

                if (route_match(route)){
                    Dialogue dialogue = new Dialogue("ID clashes", "The route ID has been taken. \n" +
                            "Either the route exists or please use a different ID.", routeadd_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(event1 -> dialogue.close());
                } else {
                    RouteOperations ops = new RouteOperations(route);
                    ops.create();
                    Stage thisStage = (Stage) routeadd_root.getScene().getWindow();
                    thisStage.close();
                }
            }
        });
    }

    //TODO: Reset button

    @Override
    public <T> void initData(T param) {
    }
}
