package org.svseas.utils.manipulator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.operations.PortOperations;
import org.svseas.operations.RouteOperations;
import org.svseas.operations.XMLOperation;
import org.svseas.utils.Tester;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 11:43.
 */
@SuppressWarnings("unchecked")
public abstract class RouteManipulator extends Manipulator {

    protected boolean route_match(Route<Port> route) {
        RouteOperations ops = new RouteOperations();
        return ops.read(route.getRouteId());
    }

    protected void loadPort(JFXComboBox<String> source, JFXComboBox<String> dest) {
        PortOperations ops = new PortOperations();
        ObjectList<Port> portList = ops.read();
        ObservableList<String> portNames = FXCollections.observableArrayList();
        for (Port port : portList.getList()){
            portNames.add(port.getName());
        }
        source.setItems(portNames);
        dest.setItems(portNames);
    }

    //source, destination listeners, number of ports, length type, distance
    protected ArrayList<Port> numberOfPorts(JFXComboBox source, JFXComboBox dest, Label noOfPorts, Label length,
                                            Label distance) {
        ObjectList<Port> portList = (ObjectList<Port>) new XMLOperation(ObjectList.class, Port.class).read(DataFile.PORT);
        ArrayList<Port> newPortList = new ArrayList<>();
        source.getSelectionModel().selectedItemProperty().addListener(listener(source, dest, noOfPorts, length,
                portList, newPortList, distance));
        dest.getSelectionModel().selectedItemProperty().addListener(listener(source, dest, noOfPorts, length,
                portList, newPortList, distance));

        return newPortList;
    }

    @NotNull
    private ChangeListener listener(JFXComboBox source, JFXComboBox dest, Label noOfPorts, Label length,
                                    ObjectList<Port> portList, ArrayList<Port> newPortList, Label distance){
        return (observable, oldValue, newValue) -> {
            if (dest.getSelectionModel().getSelectedItem() != null &&
                    source.getSelectionModel().getSelectedItem() != null) {

                //TODO: Fix the reverse route's distance (reverse and converse shall be equal)
                int end = dest.getSelectionModel().getSelectedIndex();
                int start = source.getSelectionModel().getSelectedIndex();
                int number = Math.abs(end - start);
                noOfPorts.setText(String.valueOf(number));

                if (end - start < 0) {
                    for (int i = start; i >= end; i--) {
                        Port port = portList.get(i);
                        newPortList.add(port);
                    }
                } else if (end - start > 0) {
                    for (int i = start; i <= end; i++) {
                        Port port = portList.get(i);
                        newPortList.add(port);
                    }
                }

                Platform.runLater(() -> {
                    double nm = 0.0d;

                    if (newPortList.size() > 1){
                        Tester.SUCCESS.printer();
                        for (int i = 0; i < newPortList.size() - 1; i++){
                            Port port = newPortList.get(i);
                            nm = nm + Double.parseDouble(port.getDistance_nextPort());
                        }
                        distance.setText(String.valueOf(nm));
                    } else {
                        Tester.FAIL.printer();
                        distance.setText("0");
                    }
                });


                if (number >= 15) {
                    Platform.runLater(() -> length.setText("Long"));

                } else if (number <= 14 && number >= 8) {
                    Platform.runLater(() -> length.setText("Medium"));

                } else if (number <= 7 && number >= 1) {
                    Platform.runLater(() -> length.setText("Short"));

                } else Platform.runLater(() -> length.setText("None"));
            }
        };
    }

    //charge textfield, distance listeners, total
    protected void totalCharges(Label total, Label distance, JFXTextField charge){
        distance.textProperty().addListener(chargeListener(total, distance, charge));
        charge.textProperty().addListener(chargeListener(total, distance, charge));
    }

    @NotNull
    private ChangeListener chargeListener(Label total, Label distance, JFXTextField chargeField){
        return (observable, oldValue, newValue) -> {
            if (newValue == "0" || newValue == ""){
                total.setText("0.00");
            }
            if (!Objects.equals(distance.getText(), "0") && !Objects.equals(chargeField.getText(), "")) {
                double total_charge;
                double dist = Double.parseDouble(distance.getText());
                double charge = Double.parseDouble(chargeField.getText());
                if (!Objects.equals(distance.getText(), "0")){
                    total_charge = dist * charge;
                    total.setText(String.format("%.2f", total_charge));
                }
            }
        };
    }

    public abstract void manipulate(JFXButton button);

    @Override
    public abstract <T> void initData(T param);
}
