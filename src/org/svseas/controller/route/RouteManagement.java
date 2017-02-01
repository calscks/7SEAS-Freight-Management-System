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
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.model.table.RouteInTable;
import org.svseas.operations.RouteOperations;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 10:43.
 */
@SuppressWarnings("Duplicates")


public class RouteManagement {
    @FXML
    private StackPane routemgnt_root;
    @FXML
    private JFXTreeTableView<RouteInTable> table_route;
    @FXML
    private JFXTreeTableColumn<RouteInTable, String> routeId, routeName, travel, length, distance;
    @FXML
    private JFXTreeTableColumn<RouteInTable, Double> rate, charge_total;
    @FXML
    private JFXButton btn_addRoute, btn_refreshRoute, btn_routePreview, btn_editRoute, btn_delRoute;
    @FXML
    private JFXTextField field_routeSearch;

    @FXML
    public void initialize() {
        valueFactory();

        ButtonStageShow adder = new ButtonStageShow(btn_addRoute, "/org/svseas/view/RouteAdd.fxml", "Add Route");
        adder.operate();

        btn_editRoute.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_route.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Route Not Selected",
                            "Route is not selected. Please select a route from the table to edit.",
                            routemgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    RouteInTable routeInTable = table_route.getSelectionModel().getSelectedItem().getValue();
                    RouteOperations ops = new RouteOperations();
                    ObjectList<Route<Port>> routeList = ops.read();
                    for (Route route : routeList.getList()) {
                        if (Objects.equals(routeInTable.getRouteId(), route.getRouteId())) {
                            ButtonStageShow editor = new ButtonStageShow("/org/svseas/view/RouteEdit.fxml",
                                    "Modify Route");
                            editor.<Route, RouteEdit>operate(route);
                        }
                    }
                }
            }
        });

        btn_refreshRoute.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) Platform.runLater(this::populate);
        });

        btn_delRoute.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_route.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Route Not Selected",
                            "Route is not selected. Please select a route from the table to delete.",
                            routemgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    RouteInTable routeInTable = table_route.getSelectionModel().getSelectedItem().getValue();
                    RouteOperations ops = new RouteOperations();
                    ops.delete(routeInTable.getRouteId());
                    Platform.runLater(this::populate);
                }
            }
        });

        btn_routePreview.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_route.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Route Not Selected",
                            "Route is not selected. Please select a route from the table to preview its port.",
                            routemgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    Dialogue dialogue = new Dialogue("Previewing Port Travel",
                            table_route.getSelectionModel().getSelectedItem().getValue().getTravel(),
                            routemgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                }
            }
        });

        field_routeSearch.textProperty().addListener((observable, oldValue, newValue) ->
                table_route.setPredicate(route -> route.getValue().getDistance().contains(newValue) ||
                        route.getValue().getRouteId().contains(newValue) ||
                        route.getValue().getRouteLength().contains(newValue) ||
                        route.getValue().getRouteName().contains(newValue) ||
                        route.getValue().getTravel().contains(newValue)));
    }

    private void valueFactory() {
        routeId.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, String> param) -> {
            if (routeId.validateValue(param))
                return param.getValue().getValue().routeIdProperty();
            else
                return routeId.getComputedValue(param);
        });
        routeName.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, String> param) -> {
            if (routeName.validateValue(param))
                return param.getValue().getValue().routeNameProperty();
            else
                return routeName.getComputedValue(param);
        });
        travel.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, String> param) -> {
            if (travel.validateValue(param))
                return param.getValue().getValue().travelProperty();
            else
                return travel.getComputedValue(param);
        });
        distance.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, String> param) -> {
            if (distance.validateValue(param))
                return param.getValue().getValue().distanceProperty();
            else
                return distance.getComputedValue(param);
        });
        length.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, String> param) -> {
            if (length.validateValue(param))
                return param.getValue().getValue().routeLengthProperty();
            else
                return length.getComputedValue(param);
        });
        rate.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, Double> param) ->
                param.getValue().getValue().ratePerNmProperty().asObject());
        charge_total.setCellValueFactory((TreeTableColumn.CellDataFeatures<RouteInTable, Double> param) ->
                param.getValue().getValue().totalProperty().asObject());

        List<JFXTreeTableColumn<RouteInTable, Double>> columns = Arrays.asList(rate, charge_total);
        for (JFXTreeTableColumn<RouteInTable, Double> column : columns) {
            column.setCellFactory(param -> new TreeTableCell<RouteInTable, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) setText(null);
                    else setText(String.format("%.2f", item));
                }
            });
        }

        populate();
    }

    private void populate() {
        RouteOperations ops = new RouteOperations();
        ObjectList<Route<Port>> routeList = ops.read();
        ObservableList<RouteInTable> routesInTable = FXCollections.observableArrayList();
        if (routeList != null) {
            for (Route<Port> route : routeList.getList()) {
                RouteInTable routeInTable = new RouteInTable(route.getRouteId(), route.getRouteName(),
                        route.getRouteLength(), route.getTravel(), route.getDistance(),
                        Double.parseDouble(route.getRatePerNm()), Double.parseDouble(route.getRatePerNm()));

                routesInTable.add(routeInTable);
            }
            table_route.setRoot(new RecursiveTreeItem<>(routesInTable, RecursiveTreeObject::getChildren));
            table_route.setShowRoot(false);
        }
    }


}
