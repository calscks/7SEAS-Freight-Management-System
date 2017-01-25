package org.svseas.controller.route;

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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.RouteShip;
import org.svseas.model.table.RouteShipInTable;
import org.svseas.operations.RouteShipOperations;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:47.
 */
public class RouteShipManagement {
    @FXML
    private StackPane assign_root;
    @FXML
    private JFXTreeTableView<RouteShipInTable> table_assignedShips;
    @FXML
    private JFXTreeTableColumn<RouteShipInTable, String> assignedShip, routeName;
    @FXML
    private JFXButton btn_assign, btn_refresh, btn_del;
    @FXML
    private JFXTextField field_search;

    @FXML
    public void initialize() {
        assignedShip.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteShipInTable, String> param) -> {
            if (assignedShip.validateValue(param))
                return param.getValue().getValue().ship_idProperty();
            else
                return assignedShip.getComputedValue(param);
        });
        routeName.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteShipInTable, String> param) -> {
            if (routeName.validateValue(param))
                return param.getValue().getValue().route_nameProperty();
            else
                return routeName.getComputedValue(param);
        });

        populate();

        ButtonStageShow adder = new ButtonStageShow(btn_assign, "/org/svseas/view/RouteShipAdd.fxml", "Assign Ship to Route");
        adder.operate();

        btn_refresh.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) populate();
        });

        btn_del.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_assignedShips.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Assign Values Not Selected",
                            "Nothing is selected to delete. Please select from the table.",
                            assign_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    RouteShipInTable routeShipInTable = table_assignedShips.getSelectionModel().getSelectedItem()
                            .getValue();
                    RouteShipOperations ops = new RouteShipOperations();
                    ops.delete(routeShipInTable.getShip_id(), routeShipInTable.getRoute_name());
                    Platform.runLater(this::populate);
                }
            }
        });

        field_search.textProperty().addListener((observable, oldValue, newValue) ->
                table_assignedShips.setPredicate(routeShip -> routeShip.getValue().getRoute_name().contains(newValue) ||
                        routeShip.getValue().getShip_id().contains(newValue)));
    }

    private void populate() {
        RouteShipOperations ops = new RouteShipOperations();
        ObjectList<RouteShip> routeshipList = ops.read();
        ObservableList<RouteShipInTable> assigns = FXCollections.observableArrayList();
        if (DataFile.analyse(DataFile.ROUTE_SHIP)) {
            for (RouteShip routeShip : routeshipList.getList()) {
                RouteShipInTable routeShipInTable = new RouteShipInTable(routeShip.getShip_id(), routeShip.getRoute_name());
                assigns.add(routeShipInTable);
            }
            table_assignedShips.setRoot(new RecursiveTreeItem<>(assigns, RecursiveTreeObject::getChildren));
            table_assignedShips.setShowRoot(false);
        }
    }
}
