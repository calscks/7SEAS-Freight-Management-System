package org.svseas.controller.freight;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.controller.route.RouteEdit;
import org.svseas.model.ObjectList;
import org.svseas.model.freight.Freight;
import org.svseas.model.route.Route;
import org.svseas.model.table.FreightInTable;
import org.svseas.model.table.RouteInTable;
import org.svseas.model.table.ShipInTable;
import org.svseas.operations.FreightOperations;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:11.
 */
@SuppressWarnings("Duplicates")


public class FreightManagement {
    @FXML
    private StackPane freightmgnt_root;
    @FXML
    private JFXTreeTableView<FreightInTable> table_freight;
    @FXML
    private JFXTreeTableColumn<FreightInTable, String> bookId, custUsername, shipId, shipType, route, travel;
    @FXML
    private JFXTreeTableColumn<FreightInTable, Double> charge_total;
    @FXML
    private JFXButton btn_addFreight, btn_refreshFreight, btn_editFreight, btn_delFreight;
    @FXML
    private JFXTextField field_freightSearch;

    @FXML
    public void initialize(){
        cellValueFactory();

        ButtonStageShow adder = new ButtonStageShow(btn_addFreight, "/org/svseas/view/FreightAdd.fxml", "Add Freight");
        adder.operate();

        btn_editFreight.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (table_freight.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Freight Not Selected",
                            "Freight is not selected. Please select a freight from the table to edit.",
                            freightmgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    FreightInTable freightInTable = table_freight.getSelectionModel().getSelectedItem().getValue();
                    FreightOperations ops = new FreightOperations();
                    ObjectList<Freight> list = ops.read();
                    for (Freight freight : list.getList()){
                        if (Objects.equals(freight.getBookingId(), freightInTable.getBookingId())){
                            ButtonStageShow editor = new ButtonStageShow("/org/svseas/view/FreightEdit.fxml",
                                    "Modify Freight");
                            editor.<Freight, FreightEdit>operate(freight);
                        }
                    }
                }
            }
        });

        btn_refreshFreight.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) Platform.runLater(this::populate);
        });

        btn_delFreight.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (table_freight.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Freight Not Selected",
                            "Freight is not selected. Please select a freight from the table to edit.",
                            freightmgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    FreightInTable freightInTable = table_freight.getSelectionModel().getSelectedItem().getValue();
                    FreightOperations ops = new FreightOperations();
                    ops.delete(freightInTable.getBookingId());
                    Platform.runLater(this::populate);
                }
            }
        });
    }

    private void cellValueFactory(){
        bookId.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (bookId.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return bookId.getComputedValue(param);
        });
        custUsername.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (custUsername.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return custUsername.getComputedValue(param);
        });
        shipId.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (shipId.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return shipId.getComputedValue(param);
        });
        shipType.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (shipType.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return shipType.getComputedValue(param);
        });
        route.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (route.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return route.getComputedValue(param);
        });
        travel.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, String> param) -> {
            if (travel.validateValue(param))
                return param.getValue().getValue().bookingIdProperty();
            else
                return travel.getComputedValue(param);
        });
        charge_total.setCellValueFactory((TreeTableColumn.CellDataFeatures<FreightInTable, Double> param) ->
                param.getValue().getValue().totalChargesProperty().asObject());
        charge_total.setCellFactory(param -> new TreeTableCell<FreightInTable, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setText(null);
                else setText(String.format("%.2f", item));
            }
        });

        populate();
    }

    private void populate(){
        FreightOperations ops = new FreightOperations();
        ObjectList<Freight> flist = ops.read();
        ObservableList<FreightInTable> frOlist = FXCollections.observableArrayList();
        for (Freight freight : flist.getList()){
            FreightInTable freightInTable = new FreightInTable(freight.getBookingId(), freight.getCustomer(), freight.getSource(),
                    freight.getDestination(), freight.getRoute(), freight.getTravel(), freight.getCargoWeight(),
                    freight.getCargoType(), Double.parseDouble(freight.getTotalCharges()));
            frOlist.add(freightInTable);
        }

        table_freight.setRoot(new RecursiveTreeItem<>(frOlist, RecursiveTreeObject::getChildren));
        table_freight.setShowRoot(false);
    }
}
