package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.model.ObjectList;
import org.svseas.model.ship.Ship;
import org.svseas.model.table.ShipInTable;
import org.svseas.operations.ShipOperations;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 12:55.
 */
public class ShipManagement {
    @FXML
    private StackPane ship_root;
    @FXML
    private JFXTreeTableView<ShipInTable> table_ship;
    @FXML
    private JFXTreeTableColumn<ShipInTable, String> username, ship_id, ship_type, ship_country, max_load, leasing_type, leasing_period;
    @FXML
    private JFXButton btn_addShip, btn_refreshShip, btn_viewDetails, btn_editShip, btn_delShip;
    @FXML
    private JFXTextField field_shipSearch;

    @FXML
    public void initialize() {
        username.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (username.validateValue(param))
                return param.getValue().getValue().usernameProperty();
            else
                return username.getComputedValue(param);
        });
        ship_id.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (ship_id.validateValue(param))
                return param.getValue().getValue().ship_idProperty();
            else
                return ship_id.getComputedValue(param);
        });
        ship_type.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (ship_type.validateValue(param))
                return param.getValue().getValue().typeProperty();
            else
                return ship_type.getComputedValue(param);
        });
        ship_country.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (ship_country.validateValue(param))
                return param.getValue().getValue().countryProperty();
            else
                return ship_country.getComputedValue(param);
        });
        max_load.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (max_load.validateValue(param))
                return param.getValue().getValue().maxLoadProperty();
            else
                return max_load.getComputedValue(param);
        });
        leasing_type.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (leasing_type.validateValue(param))
                return param.getValue().getValue().leaseTypeProperty();
            else
                return leasing_type.getComputedValue(param);
        });
        leasing_period.setCellValueFactory((TreeTableColumn.CellDataFeatures<ShipInTable, String> param) -> {
            if (leasing_period.validateValue(param))
                return param.getValue().getValue().periodProperty();
            else
                return leasing_period.getComputedValue(param);
        });

        populate();

        field_shipSearch.textProperty().addListener((observable, oldValue, newValue) -> new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> table_ship.setPredicate(shipItem ->
                        shipItem.getValue().getUsername().contains(newValue) ||
                                shipItem.getValue().getValue().contains(newValue) ||
                                shipItem.getValue().getLeaseType().contains(newValue) ||
                                shipItem.getValue().getShip_id().contains(newValue) ||
                                shipItem.getValue().getCountry().contains(newValue) ||
                                shipItem.getValue().getMaxLoad().contains(newValue) ||
                                shipItem.getValue().getType().contains(newValue) ||
                                shipItem.getValue().getPeriod().contains(newValue)
                ));
                return null;
            }
        }.run());

        ButtonStageShow adder = new ButtonStageShow(btn_addShip, "/org/svseas/view/ShipAdd.fxml", "Add Ship");
        adder.operate();

        btn_editShip.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_ship.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Ship Not Selected",
                            "Ship is not selected. Please select a ship from the table to edit.",
                            ship_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    ShipInTable shipInTable = table_ship.getSelectionModel().getSelectedItem().getValue();
                    ShipOperations ops = new ShipOperations();
                    ObjectList<Ship> shipList = ops.read();
                    for (Ship ship : shipList.getList()) {
                        if (Objects.equals(shipInTable.getShip_id(), ship.getShip_id())) {
                            ButtonStageShow editor = new ButtonStageShow("/org/svseas/view/ShipEdit.fxml",
                                    "Edit Ship");
                            editor.<Ship, ShipEdit>operate(ship);
                        }
                    }
                }
            }
        });

        btn_refreshShip.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) Platform.runLater(this::populate);
        });

        btn_viewDetails.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_ship.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Ship Not Selected",
                            "Ship is not selected. Please select a ship from the table to view its details.",
                            ship_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    ShipInTable shipInTable = table_ship.getSelectionModel().getSelectedItem().getValue();
                    ShipOperations ops = new ShipOperations();
                    ObjectList<Ship> shipList = ops.read();
                    for (Ship ship : shipList.getList()) {
                        if (Objects.equals(shipInTable.getShip_id(), ship.getShip_id())) {
                            ButtonStageShow viewer = new ButtonStageShow("/org/svseas/view/ShipMoreDetails.fxml",
                                    "Ship Details");
                            viewer.<Ship, ShipDetails>operate(ship);
                        }
                    }
                }
            }
        });

        btn_delShip.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_ship.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Ship Not Selected",
                            "Ship is not selected. Please select a ship from the table to delete.",
                            ship_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    ShipInTable shipInTable = table_ship.getSelectionModel().getSelectedItem().getValue();
                    ShipOperations ops = new ShipOperations();
                    ops.delete(shipInTable.getShip_id());
                    Platform.runLater(this::populate);
                }
            }
        });
    }

    private void populate() {
        ShipOperations ops = new ShipOperations();
        ObjectList<Ship> shipList = ops.read();
        ObservableList<ShipInTable> shipInTables = FXCollections.observableArrayList();
        if (shipList != null) {
            for (Ship ship : shipList.getList()) {
                ShipInTable shipInTable = new ShipInTable(ship.getShip_id(), ship.getUsername(), ship.getType(),
                        ship.getMaxLoad(), ship.getValue(), ship.getCountry(), ship.getPeriod(),
                        ship.getLeaseType().toString());
                shipInTables.add(shipInTable);
            }
            table_ship.setRoot(new RecursiveTreeItem<>(shipInTables, RecursiveTreeObject::getChildren));
            table_ship.setShowRoot(false);
        }
    }

}
