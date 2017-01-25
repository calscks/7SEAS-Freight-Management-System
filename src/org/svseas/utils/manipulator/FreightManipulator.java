package org.svseas.utils.manipulator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.CustomerAccount;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.model.route.RouteShip;
import org.svseas.model.ship.Ship;
import org.svseas.operations.AccountOperations;
import org.svseas.operations.RouteOperations;
import org.svseas.operations.RouteShipOperations;
import org.svseas.operations.ShipOperations;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:13.
 */
@SuppressWarnings("unchecked")
public abstract class FreightManipulator extends RouteManipulator {

    protected void loadCustomerId(JFXComboBox<String> customer){
        AccountOperations accops = new AccountOperations(DataFile.CUSTOMER);
        ObjectList<CustomerAccount> custList = (ObjectList<CustomerAccount>) accops.read();
        ObservableList<String> usernameList = FXCollections.observableArrayList();

        for (CustomerAccount account : custList.getList()){
            usernameList.add(account.getUsername());
        }

        customer.setItems(usernameList);
    }

    @Override
    protected void loadPort(JFXComboBox<String> source, JFXComboBox<String> dest) {
        super.loadPort(source, dest);
    }

    //enable loading the routes, source & dest listeners
    protected void enableLoadRoute(JFXComboBox<String> route, JFXComboBox<String> source, JFXComboBox<String> dest){
        BooleanBinding binding = source.getSelectionModel().selectedItemProperty().isNull()
                .or(dest.getSelectionModel().selectedItemProperty().isNull());
        route.disableProperty().bind(binding);

        source.getSelectionModel().selectedItemProperty().addListener(listener(route, source, dest));
        dest.getSelectionModel().selectedItemProperty().addListener(listener(route, source, dest));
    }

    @NotNull
    private ChangeListener listener(JFXComboBox routeBox, JFXComboBox source, JFXComboBox dest){
        RouteOperations ops = new RouteOperations();
        ObjectList<Route<Port>> routeList = ops.read();
        return (observable, oldValue, newValue) -> {
            String start = (String) source.getSelectionModel().getSelectedItem();
            String end = (String) dest.getSelectionModel().getSelectedItem();
            ObservableList<String> routeNames = FXCollections.observableArrayList();
            if (start != null && end != null){
                for (Route<Port> route : routeList.getList()){
                    if (Objects.equals(start, route.get(0).getName()) && Objects.equals(end, route.getLast().getName())){
                        routeNames.add(route.getRouteName());
                    }
                }
                routeBox.setItems(routeNames);
            }
        };
    }

    //load ship based on route, route listener. Returns the route list
    protected ObjectList<Route<Port>> loadRouteShip(JFXComboBox<String> route, JFXComboBox<Ship> shipBox, Label route_charge){
        ObjectList<Route<Port>> routeList = new RouteOperations().read();
        ObjectList<Ship> shipList = new ShipOperations().read();
        ObjectList<RouteShip> routeShipList = new RouteShipOperations().read();
        Callback cellFactory = new Callback<ListView<Ship>, ListCell<Ship>>() {
            @Override
            public ListCell<Ship> call(ListView<Ship> listView) {
                return new ListCell<Ship>(){
                    @Override
                    protected void updateItem(Ship item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getType() + " - " + item.getShip_id());
                        }
                    }
                };
            }
        };
        shipBox.setButtonCell((ListCell) cellFactory.call(null));
        shipBox.setCellFactory(cellFactory);
        shipBox.disableProperty().bind(route.getSelectionModel().selectedItemProperty().isNull());
        route.getSelectionModel().selectedItemProperty().addListener(routeListener(route, shipBox, route_charge, routeList,
                shipList, routeShipList));

        return routeList;
    }

    @NotNull
    private ChangeListener routeListener(JFXComboBox<String> route, JFXComboBox<Ship> shipBox, Label route_charge,
                                         ObjectList<Route<Port>> routeList, ObjectList<Ship> shipList,
                                         ObjectList<RouteShip> routeShipList){
        return (observable, oldValue, newValue) -> {
            ObservableList<Ship> ships = FXCollections.observableArrayList();
            for (RouteShip routeShip : routeShipList.getList()){
                if (Objects.equals(route.getSelectionModel().getSelectedItem(), routeShip.getRoute_name())){
                    System.out.println("Found");
                    for (Ship ship : shipList.getList()){
                        if (Objects.equals(routeShip.getShip_id(), ship.getShip_id())){
                            ships.add(ship);
                            System.out.println("Ship added");
                        }
                    }

                    for (Route<Port> portRoute : routeList.getList()){
                        if (Objects.equals(route.getSelectionModel().getSelectedItem(), portRoute.getRouteName()))
                            route_charge.setText(portRoute.getTotal());
                    }
                }
            }
            shipBox.setItems(ships);
        };
    }

    //set ship charges based on ship type
    protected void loadShipCharge(JFXComboBox<Ship> shipBox, Label ship_charges){
        shipBox.getSelectionModel().selectedItemProperty().addListener(shipListener(shipBox, ship_charges));
    }

    private ChangeListener shipListener(JFXComboBox<Ship> shipBox, Label ship_charges){
        return (observable, oldValue, newValue) -> {
            String shipType = shipBox.getSelectionModel().getSelectedItem().getType();
            System.out.println(shipType);
            if (Objects.equals(shipType, "Containers"))
                ship_charges.setText("70.00");
            else if (Objects.equals(shipType, "Barge"))
                ship_charges.setText("30.00");
            else if (Objects.equals(shipType, "Tankers"))
                ship_charges.setText("40.00");
            else if (Objects.equals(shipType, "Tugboats"))
                ship_charges.setText("20.00");
            else if (Objects.equals(shipType, "Cargo"))
                ship_charges.setText("50.00");
            else if (Objects.equals(shipType, "Cruises"))
                ship_charges.setText("80.00");
            else ship_charges.setText("0.00");
        };
    }

    //find type of cargo and calculate cargo charges, weight field listener
    protected void cargoCalculator(JFXTextField weightField, Label cargo_type, Label cargo_charge){
        weightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, "")){
                cargo_type.setText("None");
                cargo_charge.setText("0.00");
            }else if (Double.parseDouble(newValue) >= 5){
                cargo_type.setText("TL");
                double rate = 120d;
                double charge = rate * Double.parseDouble(newValue);
                cargo_charge.setText(String.format("%.2f", charge));
            }else if (Double.parseDouble(newValue) < 5 && Double.parseDouble(newValue) >= 0){
                cargo_type.setText("LTL");
                double rate = 150d;
                double charge = rate * Double.parseDouble(newValue);
                cargo_charge.setText(String.format("%.2f", charge));
            }
        });
    }

    //full of label's listener
    protected void totalCalculator(Label cargo_charge, Label ship_charge, Label route_charge, Label total_charge) {
        cargo_charge.textProperty().addListener(totalListener(cargo_charge, ship_charge, route_charge, total_charge));
        ship_charge.textProperty().addListener(totalListener(cargo_charge, ship_charge, route_charge, total_charge));
        route_charge.textProperty().addListener(totalListener(cargo_charge, ship_charge, route_charge, total_charge));
    }

    private ChangeListener totalListener(Label cargo_charge, Label ship_charge, Label route_charge, Label total_charge){
        return (observable, oldValue, newValue) -> {
            if (!Objects.equals(cargo_charge.getText(), "0.00") && !Objects.equals(ship_charge.getText(), "0.00")
                    && !Objects.equals(route_charge.getText(), "0.00")){
                double total = Double.parseDouble(cargo_charge.getText()) + Double.parseDouble(ship_charge.getText())
                        + Double.parseDouble(route_charge.getText());
                total_charge.setText(String.format("%.2f", total));
            } else {
                total_charge.setText("0.00");
            }
        };
    }


    public abstract void manipulate(JFXButton button);

    @Override
    public abstract  <T> void initData(T param);
}
