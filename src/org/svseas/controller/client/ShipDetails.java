package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
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
import org.svseas.utils.Previewer;
import org.svseas.utils.ShipManipulator;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:20.
 */
public class ShipDetails extends ShipManipulator {
    @FXML
    private StackPane moredetails_root;
    @FXML
    private JFXTextField ship_id, max_load, ship_value, contract_period;
    @FXML
    private JFXComboBox<String> cbox_username, cbox_shipType, cbox_country;
    @FXML
    private JFXComboBox<Integer> cbox_contractPeriod;
    @FXML
    private JFXButton btn_close, btn_conPreview, btn_oprPreview;
    @FXML
    private JFXDrawer drawer_contract, drawer_operating;
    @FXML
    private JFXRadioButton radio_contract, radio_operating;
    @FXML
    private Label label_oprTotal, label_conTotal, label_shipVal;
    @FXML
    private ToggleGroup lease_type;

    @FXML
    public void initialize() {
        ObservableList<Preview> contractList = contractHireCalculator(contract_period, ship_value, label_shipVal, label_conTotal);
        ObservableList<Preview> operatingList = operatingLeaseCalculator(cbox_contractPeriod, ship_value, label_oprTotal);

        new Previewer(btn_conPreview, label_conTotal, contractList, moredetails_root);
        new Previewer(btn_oprPreview, label_oprTotal, operatingList, moredetails_root);

        btn_close.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Stage thisStage = (Stage) moredetails_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    @Override
    public void manipulate(JFXButton button) {
    }

    @Override
    @SuppressWarnings("Duplicates")
    public <T> void initData(T ship) {
        if (ship instanceof Ship) {
            cbox_username.getSelectionModel().select(((Ship) ship).getUsername());
            ship_id.setText(((Ship) ship).getShip_id());
            cbox_shipType.getSelectionModel().select(((Ship) ship).getType());
            max_load.setText(((Ship) ship).getMaxLoad());
            ship_value.setText(((Ship) ship).getValue());
            cbox_country.getSelectionModel().select(((Ship) ship).getCountry());
            if (((Ship) ship).getLeaseType() == LeaseType.CONTRACT) {
                radio_contract.setSelected(true);
                contract_period.setText(((Ship) ship).getPeriod());
            } else if (((Ship) ship).getLeaseType() == LeaseType.OPERATION) {
                radio_operating.setSelected(true);
                cbox_contractPeriod.getSelectionModel().select(Integer.valueOf(((Ship) ship).getPeriod()));
            }
        }
    }
}
