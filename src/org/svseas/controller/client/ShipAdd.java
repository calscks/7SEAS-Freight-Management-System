package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import org.svseas.utils.ShipManipulator;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:20.
 */
public class ShipAdd extends ShipManipulator {
    @FXML
    private StackPane shipadd_root;
    @FXML
    private JFXTextField ship_id, max_load, ship_value, contract_period;
    @FXML
    private JFXComboBox<String> cbox_username, cbox_shipType, cbox_country, cbox_contractPeriod;
    @FXML
    private JFXButton btn_add, btn_reset, btn_conPreview, btn_oprPreview;
    @FXML
    private JFXDrawer drawer_contract, drawer_operating;
    @FXML
    private JFXRadioButton radio_contract, radio_operating;
    @FXML
    private Label label_oprTotal, label_conTotal, label_shipVal;
    @FXML private ToggleGroup lease_type;

    @FXML
    public void initialize() {
        //drawer_contract.setDisable(false);
        loadCountries(cbox_country);
        loadUsername(cbox_username);
        loadType(cbox_shipType);
        switcher(radio_contract, radio_operating, drawer_contract, drawer_operating, lease_type, ship_value);
        contractHireCalculator(contract_period, ship_value, label_shipVal, label_conTotal);
    }
}
