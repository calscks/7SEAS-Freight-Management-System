package org.svseas.controller.freight;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.freight.Freight;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.model.ship.Ship;
import org.svseas.operations.FreightOperations;
import org.svseas.utils.manipulator.FreightManipulator;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:11.
 */
public class FreightAdd extends FreightManipulator {
    @FXML
    private StackPane freightadd_root;
    @FXML
    private Label label_bookID, label_typeOfCargo, label_cargoCharge, label_shipCharge, label_routeCharge, label_total;
    @FXML
    private JFXComboBox<String> cbox_source, cbox_route, cbox_customer, cbox_destination;
    @FXML
    private JFXComboBox<Ship> cbox_ship;
    @FXML
    private JFXTextField cargo_weight;
    @FXML
    private JFXButton btn_add, btn_reset;

    private ObjectList<Route<Port>> routeList;

    private Freight freight;

    private String bookingId, customer, source, destination, route, travel, cargoWeight, cargoType, shipId, totalCharges;

    @FXML
    public void initialize() {
        ObjectList<Freight> ops = new FreightOperations().read();
        if (!DataFile.analyse(DataFile.FREIGHT) || ops.size() == 0){
            label_bookID.setText("000001");
        } else {
            int last = Integer.parseInt(ops.getLast().getBookingId()) + 1;
            label_bookID.setText(String.format("%06d", last));
        }

        loadCustomerId(cbox_customer);
        loadPort(cbox_source, cbox_destination);
        enableLoadRoute(cbox_route, cbox_source, cbox_destination);
        routeList = loadRouteShip(cbox_route, cbox_ship, label_routeCharge);
        loadShipCharge(cbox_ship, label_shipCharge);
        cargoCalculator(cargo_weight, label_typeOfCargo, label_cargoCharge);
        totalCalculator(label_cargoCharge, label_shipCharge, label_routeCharge, label_total);

        BooleanBinding binding = cbox_customer.getSelectionModel().selectedItemProperty().isNull()
                .or(cbox_destination.getSelectionModel().selectedItemProperty().isNull())
                .or(cbox_route.getSelectionModel().selectedItemProperty().isNull())
                .or(cbox_ship.getSelectionModel().selectedItemProperty().isNull())
                .or(cbox_source.getSelectionModel().selectedItemProperty().isNull())
                .or(cargo_weight.textProperty().isEmpty());

        btn_add.disableProperty().bind(binding);

        manipulate(btn_add);
    }

    @Override
    public void manipulate(JFXButton button) {
        btn_add.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                for (Route route : routeList.getList()){
                    if (Objects.equals(cbox_route.getSelectionModel().getSelectedItem(), route.getRouteName())){
                        travel = route.getTravel();
                    }
                }
                bookingId = label_bookID.getText();
                customer = cbox_customer.getSelectionModel().getSelectedItem();
                source = cbox_source.getSelectionModel().getSelectedItem();
                destination = cbox_destination.getSelectionModel().getSelectedItem();
                route = cbox_route.getSelectionModel().getSelectedItem();
                cargoWeight = cargo_weight.getText();
                cargoType = label_typeOfCargo.getText();
                shipId = cbox_ship.getSelectionModel().getSelectedItem().getShip_id();
                totalCharges = label_total.getText();
                freight = new Freight(bookingId, customer, source, destination, route, travel, cargoWeight, cargoType, shipId, totalCharges);

                FreightOperations ops = new FreightOperations(freight);
                ops.create();
                Stage thisStage = (Stage) freightadd_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    //TODO: Reset Button

    @Override
    public <T> void initData(T param) {}
}
