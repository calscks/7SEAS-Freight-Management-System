package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.data.LeaseType;
import org.svseas.model.ship.Ship;
import org.svseas.model.table.Preview;
import org.svseas.operations.ShipOperations;
import org.svseas.utils.Dialogue;
import org.svseas.utils.Previewer;
import org.svseas.utils.manipulator.ShipManipulator;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:20.
 */
public class ShipAdd extends ShipManipulator {
    @FXML
    private StackPane shipadd_root;
    @FXML
    private JFXTextField ship_id, max_load, ship_value, contract_period;
    @FXML
    private JFXComboBox<String> cbox_username, cbox_shipType, cbox_country;
    @FXML
    private JFXComboBox<Integer> cbox_contractPeriod;
    @FXML
    private JFXButton btn_add, btn_reset, btn_conPreview, btn_oprPreview;
    @FXML
    private JFXDrawer drawer_contract, drawer_operating;
    @FXML
    private JFXRadioButton radio_contract, radio_operating;
    @FXML
    private Label label_oprTotal, label_conTotal, label_shipVal;
    @FXML
    private ToggleGroup lease_type;

    private Ship ship;

    @FXML
    @SuppressWarnings("Duplicates")
    public void initialize() {
        loadCountries(cbox_country);
        loadUsername(cbox_username);
        loadType(cbox_shipType);
        loadPeriod(cbox_contractPeriod);
        switcher(radio_contract, radio_operating, drawer_contract, drawer_operating, lease_type, ship_value);
        ObservableList<Preview> contractList = contractHireCalculator(contract_period, ship_value, label_shipVal, label_conTotal);
        ObservableList<Preview> operatingList = operatingLeaseCalculator(cbox_contractPeriod, ship_value, label_oprTotal);

        new Previewer(btn_conPreview, label_conTotal, contractList, shipadd_root);
        new Previewer(btn_oprPreview, label_oprTotal, operatingList, shipadd_root);

        BooleanBinding binding = ship_id.textProperty().isEmpty()
                .or(max_load.textProperty().isEmpty())
                .or(ship_value.textProperty().isEmpty())
                .or(cbox_country.selectionModelProperty().isNull())
                .or(cbox_username.selectionModelProperty().isNull())
                .or(cbox_shipType.selectionModelProperty().isNull())
                .or(lease_type.selectedToggleProperty().isNull());

        btn_add.disableProperty().bind(binding);
        manipulate(btn_add);
    }

    @Override
    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (radio_contract.isSelected()) {
                    ship = new Ship(ship_id.getText(), cbox_username.getSelectionModel().getSelectedItem(),
                            cbox_shipType.getSelectionModel().getSelectedItem(), max_load.getText(),
                            ship_value.getText(), cbox_country.getSelectionModel().getSelectedItem(),
                            contract_period.getText(), LeaseType.CONTRACT);
                } else if (radio_operating.isSelected()){
                    ship = new Ship(ship_id.getText(), cbox_username.getSelectionModel().getSelectedItem(),
                            cbox_shipType.getSelectionModel().getSelectedItem(), max_load.getText(),
                            ship_value.getText(), cbox_country.getSelectionModel().getSelectedItem(),
                            cbox_contractPeriod.getSelectionModel().getSelectedItem().toString(),
                            LeaseType.OPERATION);
                }

                if (ship_match(ship)){
                    Dialogue dialogue = new Dialogue("ID clashes", "The ship ID has been taken. \n" +
                            "Either the ship exists or please use a different ID.", shipadd_root, Dialogue.DialogueType.ACCEPT);
                    dialogue.getOk().setOnMouseClicked(event1 -> dialogue.close());
                } else {
                    ShipOperations ops = new ShipOperations(ship);
                    ops.create();
                    Stage thisStage = (Stage) shipadd_root.getScene().getWindow();
                    thisStage.close();
                }
            }
        });
    }

    //TODO: Reset button

    @Override
    public <T> void initData(T param) {}
}

