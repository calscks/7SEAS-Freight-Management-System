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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.data.LeaseType;
import org.svseas.model.ship.Ship;
import org.svseas.model.table.Preview;
import org.svseas.operations.ShipOperations;
import org.svseas.utils.Previewer;
import org.svseas.utils.Validator;
import org.svseas.utils.manipulator.ShipManipulator;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:20.
 */

//TODO: SHOW during presentation: This is inheritance (multi-level) Manipulator -> ShipManipulator -> ShipEdit

public class ShipEdit extends ShipManipulator {
    @FXML
    private StackPane shipedit_root;
    @FXML
    private JFXTextField ship_id, max_load, ship_value, contract_period;
    @FXML
    private JFXComboBox<String> cbox_username, cbox_shipType, cbox_country;
    @FXML
    private JFXComboBox<Integer> cbox_contractPeriod;
    @FXML
    private JFXButton btn_edit, btn_cancel, btn_conPreview, btn_oprPreview;
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
    public void initialize(){

        ship_id.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNo(20));
        max_load.addEventFilter(KeyEvent.KEY_TYPED, Validator.validPrice(20));
        ship_value.addEventFilter(KeyEvent.KEY_TYPED, Validator.validPrice(20));
        contract_period.addEventFilter(KeyEvent.KEY_TYPED, Validator.validNo(20));

        ship_id.setDisable(true);
        loadCountries(cbox_country);
        loadUsername(cbox_username);
        loadType(cbox_shipType);
        loadPeriod(cbox_contractPeriod);
        switcher(radio_contract, radio_operating, drawer_contract, drawer_operating, lease_type, ship_value);
        ObservableList<Preview> contractList = contractHireCalculator(contract_period, ship_value, label_shipVal, label_conTotal);
        ObservableList<Preview> operatingList = operatingLeaseCalculator(cbox_contractPeriod, ship_value, label_oprTotal);

        new Previewer(btn_conPreview, label_conTotal, contractList, shipedit_root);
        new Previewer(btn_oprPreview, label_oprTotal, operatingList, shipedit_root);

        BooleanBinding binding = ship_id.textProperty().isEmpty()
                .or(max_load.textProperty().isEmpty())
                .or(ship_value.textProperty().isEmpty())
                .or(cbox_country.getSelectionModel().selectedItemProperty().isNull())
                .or(cbox_username.getSelectionModel().selectedItemProperty().isNull())
                .or(cbox_shipType.getSelectionModel().selectedItemProperty().isNull())
                .or(lease_type.selectedToggleProperty().isNull());

        btn_edit.disableProperty().bind(binding);
        manipulate(btn_edit);

        btn_cancel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Stage thisStage = (Stage) shipedit_root.getScene().getWindow();
                thisStage.close();
            }
        });
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

                ShipOperations ops = new ShipOperations(ship);
                ops.update(ship_id.getText());
                Stage thisStage = (Stage) shipedit_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    @Override
    @SuppressWarnings("Duplicates")
    public <T> void initData(T ship) {
        if (ship instanceof Ship){
            cbox_username.getSelectionModel().select(((Ship) ship).getUsername());
            ship_id.setText(((Ship) ship).getShip_id());
            cbox_shipType.getSelectionModel().select(((Ship) ship).getType());
            max_load.setText(((Ship) ship).getMaxLoad());
            ship_value.setText(((Ship) ship).getValue());
            cbox_country.getSelectionModel().select(((Ship) ship).getCountry());
            if (((Ship) ship).getLeaseType() == LeaseType.CONTRACT){
                radio_contract.setSelected(true);
                contract_period.setText(((Ship) ship).getPeriod());
            } else if (((Ship) ship).getLeaseType() == LeaseType.OPERATION){
                radio_operating.setSelected(true);
                cbox_contractPeriod.getSelectionModel().select(Integer.valueOf(((Ship) ship).getPeriod()));
            }
        }
    }
}
