package org.svseas.controller.route;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.model.port.Port;
import org.svseas.operations.PortOperations;
import org.svseas.utils.Dialogue;
import org.svseas.utils.manipulator.PortManipulator;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 00:34.
 */
public class PortAdd extends PortManipulator {

    @FXML
    private StackPane portadd_root;
    @FXML
    private JFXTextField port_id, port_name, dist_nextPort;
    @FXML
    private JFXButton btn_add;

    @FXML
    public void initialize() {
        BooleanBinding binding = port_id.textProperty().isEmpty()
                .or(port_name.textProperty().isEmpty())
                .or(dist_nextPort.textProperty().isEmpty());
        btn_add.disableProperty().bind(binding);
        manipulate(btn_add);
    }

    @Override
    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Port port = new Port(port_id.getText(), port_name.getText(), dist_nextPort.getText());
                if (port_match(port)){
                    Dialogue dialogue = new Dialogue("ID clashes", "The port ID has been taken. \n" +
                            "Either the port exists or please use a different ID.", portadd_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(event1 -> dialogue.close());
                } else {
                    PortOperations potops = new PortOperations(port);
                    potops.create();
                    Stage thisStage = (Stage) portadd_root.getScene().getWindow();
                    thisStage.close();
                }
            }

        });
    }

    @Override
    public <T> void initData(T param) {}
}
