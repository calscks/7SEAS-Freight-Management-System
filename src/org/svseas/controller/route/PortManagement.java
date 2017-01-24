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
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.table.PortInTable;
import org.svseas.operations.PortOperations;
import org.svseas.operations.XMLOperation;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 22:28.
 */
public class PortManagement {
    @FXML
    private StackPane portmgnt_root;
    @FXML
    private JFXTreeTableView<PortInTable> table_port;
    @FXML
    private JFXTreeTableColumn<PortInTable, String> portId, portName, dist_toPort;
    @FXML
    private JFXButton btn_addPort, btn_refreshPort, btn_moveUp, btn_moveDown, btn_editPort, btn_delPort;
    @FXML
    private JFXTextField field_portSearch;

    private ObservableList<PortInTable> portInTables;

    @FXML
    public void initialize() {
        tableValueFactory();
        field_portSearch.textProperty().addListener((observable, oldValue, newValue) -> new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> table_port.setPredicate(port ->
                        port.getValue().getPortId().contains(newValue) ||
                                port.getValue().getName().contains(newValue) ||
                                port.getValue().getDist_nextPort().contains(newValue)));
                return null;
            }
        }.run());

        ButtonStageShow adder = new ButtonStageShow(btn_addPort, "/org/svseas/view/PortAdd.fxml", "Add Port");
        adder.operate();

        btn_editPort.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (table_port.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Ship Not Selected",
                            "Ship is not selected. Please select a ship from the table to edit.",
                            portmgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    PortInTable portInTable = table_port.getSelectionModel().getSelectedItem().getValue();
                    PortOperations ops = new PortOperations();
                    ObjectList<Port> portList = ops.read();
                    for (Port port : portList.getList()) {
                        if (Objects.equals(portInTable.getPortId(), port.getPortId())) {
                            ButtonStageShow editor = new ButtonStageShow("/org/svseas/view/PortEdit.fxml",
                                    "Modify Port");
                            editor.<Port, PortEdit>operate(port);
                        }
                    }
                }
            }
        });

        btn_moveUp.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                int selectedIndex = table_port.getSelectionModel().getSelectedIndex();
                if (selectedIndex != 0) {
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            PortInTable removePort = portInTables.remove(selectedIndex);
                            int newIndex = selectedIndex - 1;
                            portInTables.add(newIndex, removePort);
                            table_port.setRoot(new RecursiveTreeItem<>(portInTables, RecursiveTreeObject::getChildren));
                            table_port.setShowRoot(false);
                            focus(newIndex);

                            ObjectList<Port> portList = new ObjectList<>();
                            for (PortInTable portInTable : portInTables) {
                                Port port = new Port(portInTable.getPortId(), portInTable.getName(),
                                        portInTable.getDist_nextPort());
                                portList.add(port);
                            }
                            new XMLOperation(ObjectList.class, Port.class).write(DataFile.PORT, portList);
                            return null;
                        }
                    };
                    task.run();
                }
            }
        });

        btn_moveDown.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                int selectedIndex = table_port.getSelectionModel().getSelectedIndex();
                if (selectedIndex != table_port.getCurrentItemsCount() - 1) {
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            PortInTable removePort = portInTables.remove(selectedIndex);
                            int newIndex = selectedIndex + 1;
                            portInTables.add(newIndex, removePort);
                            table_port.setRoot(new RecursiveTreeItem<>(portInTables, RecursiveTreeObject::getChildren));
                            table_port.setShowRoot(false);
                            focus(newIndex);

                            ObjectList<Port> portList = new ObjectList<>();
                            for (PortInTable portInTable : portInTables) {
                                Port port = new Port(portInTable.getPortId(), portInTable.getName(),
                                        portInTable.getDist_nextPort());
                                portList.add(port);
                            }
                            new XMLOperation(ObjectList.class, Port.class).write(DataFile.PORT, portList);
                            return null;
                        }
                    };
                    task.run();
                }
            }
        });

        btn_refreshPort.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) populate();
        });

        btn_delPort.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (table_port.getSelectionModel().isEmpty()) {
                    Dialogue dialogue = new Dialogue("Port Not Selected",
                            "Port is not selected. Please select a port from the table to delete.",
                            portmgnt_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(e1 -> dialogue.close());
                } else {
                    PortInTable portInTable = table_port.getSelectionModel().getSelectedItem().getValue();
                    PortOperations ops = new PortOperations();
                    ops.delete(portInTable.getPortId());
                    Platform.runLater(this::populate);
                }
            }
        });
    }

    private void focus(int index) {
        table_port.requestFocus();
        table_port.getSelectionModel().select(index);
        table_port.getFocusModel().focus(index);
    }

    private void tableValueFactory() {
        portId.setCellValueFactory((TreeTableColumn.CellDataFeatures<PortInTable, String> param) -> {
            if (portId.validateValue(param))
                return param.getValue().getValue().portIdProperty();
            else
                return portId.getComputedValue(param);
        });
        portName.setCellValueFactory((TreeTableColumn.CellDataFeatures<PortInTable, String> param) -> {
            if (portName.validateValue(param))
                return param.getValue().getValue().nameProperty();
            else
                return portName.getComputedValue(param);
        });
        dist_toPort.setCellValueFactory((TreeTableColumn.CellDataFeatures<PortInTable, String> param) -> {
            if (dist_toPort.validateValue(param))
                return param.getValue().getValue().dist_nextPortProperty();
            else
                return dist_toPort.getComputedValue(param);
        });

        populate();
    }

    private void populate() {
        PortOperations ops = new PortOperations();
        ObjectList<Port> portList = ops.read();
        portInTables = FXCollections.observableArrayList();

        if (portList != null) {
            for (Port port : portList.getList()) {
                PortInTable portInTable = new PortInTable(port.getPortId(), port.getName(), port.getDistance_nextPort());
                portInTables.add(portInTable);
                table_port.setRoot(new RecursiveTreeItem<>(portInTables, RecursiveTreeObject::getChildren));
                table_port.setShowRoot(false);
            }
        }
    }
}
