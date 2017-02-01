package org.svseas.controller.route;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.model.route.RouteShip;
import org.svseas.model.ship.Ship;
import org.svseas.operations.RouteOperations;
import org.svseas.operations.RouteShipOperations;
import org.svseas.operations.ShipOperations;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:48.
 */
public class RouteShipAdd {
    @FXML
    private StackPane routeshipadd_root;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXComboBox<String> cbox_ship, cbox_route;

    @FXML
    public void initialize() {
        ShipOperations ops = new ShipOperations();
        RouteOperations ops2 = new RouteOperations();
        ObjectList<Ship> shipList = ops.read();
        ObjectList<Route<Port>> routeList = ops2.read();
        ObservableList<String> shipIds = FXCollections.observableArrayList();
        ObservableList<String> routeNames = FXCollections.observableArrayList();

        if (shipList != null) {
            for (Ship ship : shipList.getList()) {
                shipIds.add(ship.getShip_id());
            }
        }
        if (routeList != null) {
            for (Route<Port> route : routeList.getList()) {
                routeNames.add(route.getRouteName());
            }
        }

        cbox_ship.setItems(shipIds);
        cbox_route.setItems(routeNames);

        BooleanBinding binding = cbox_ship.getSelectionModel().selectedItemProperty().isNull().or(
                cbox_route.getSelectionModel().selectedItemProperty().isNull());
        btn_add.disableProperty().bind(binding);

        btn_add.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                RouteShip routeShip = new RouteShip(cbox_ship.getSelectionModel().getSelectedItem(),
                        cbox_route.getSelectionModel().getSelectedItem());
                RouteShipOperations ops3 = new RouteShipOperations(routeShip);
                if (ops3.read(routeShip.getShip_id(), routeShip.getRoute_name())) {
                    Dialogue dialogue = new Dialogue("Found", "The ship has already been assigned to this route before!",
                            routeshipadd_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(event1 -> dialogue.close());
                } else {
                    ops3.create();
                    Stage thisStage = (Stage) routeshipadd_root.getScene().getWindow();
                    thisStage.close();
                }
            }
        });
    }
}
