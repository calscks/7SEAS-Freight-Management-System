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
import org.svseas.utils.manipulator.RouteManipulator;

import java.util.ArrayList;
/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:43.
 */
@SuppressWarnings({"unchecked"})

public class RouteEdit extends RouteManipulator{

    @FXML
    private StackPane routeedit_root;
    @FXML
    private JFXTextField route_id, charge_per_nm, route_name;
    @FXML
    private JFXComboBox<String> cbox_source, cbox_destination;
    @FXML
    private JFXButton btn_edit, btn_cancel;
    @FXML
    private Label label_noPorts, label_dist, label_length, label_total;

    private ArrayList<Port> ports;

    private Route<Port> route;

    @FXML
    public void initialize(){
        route_id.setDisable(true);
        loadPort(cbox_source, cbox_destination);
        ports = numberOfPorts(cbox_source, cbox_destination, label_noPorts, label_length, label_dist);
        totalCharges(label_total, label_dist, charge_per_nm);

        BooleanBinding binding = route_id.textProperty().isEmpty()
                .or(charge_per_nm.textProperty().isEmpty())
                .or(route_name.textProperty().isEmpty())
                .or(cbox_source.selectionModelProperty().isNull())
                .or(cbox_destination.selectionModelProperty().isNull());
        btn_edit.disableProperty().bind(binding);

        manipulate(btn_edit);
    }

    @Override
    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                ArrayList<String> portName = new ArrayList<>();
                for (Port port : ports){
                    portName.add(port.getName());
                }
                String travel = String.join(" -> ", portName);
                System.out.println(travel);
                route = new Route<>(route_id.getText(), route_name.getText(), label_length.getText(),
                        charge_per_nm.getText(), label_total.getText(), travel, label_dist.getText());
                route.setPortList(ports);
                RouteOperations ops = new RouteOperations(route);
                ops.update(route_id.getText());
                Stage thisStage = (Stage) routeedit_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    //TODO: cancel button

    @Override
    public <T> void initData(T route) {
        if (route instanceof Route){
            route_id.setText(((Route) route).getRouteId());
            route_name.setText(((Route) route).getRouteName());
            cbox_source.getSelectionModel().select(((Route<Port>) route).get(0).getName());
            cbox_destination.getSelectionModel().select(((Route<Port>) route).getLast().getName());
            charge_per_nm.setText(((Route) route).getRatePerNm());
        }
    }
}
