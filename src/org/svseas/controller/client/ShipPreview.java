package org.svseas.controller.client;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.svseas.model.table.Preview;
import org.svseas.utils.manipulator.Manipulator;

import java.util.Arrays;
import java.util.List;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 15:06.
 */
public class ShipPreview extends Manipulator {
    @FXML
    private TableView<Preview> table_preview;
    @FXML
    private StackPane preview_root;
    @FXML
    private TableColumn<Preview, Integer> year;
    @FXML
    private TableColumn<Preview, Double> ship_value, lease_amount, cumulative;

    @FXML
    public void initialize() {
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        ship_value.setCellValueFactory(new PropertyValueFactory<>("ship_value"));
        lease_amount.setCellValueFactory(new PropertyValueFactory<>("payment"));
        cumulative.setCellValueFactory(new PropertyValueFactory<>("cumulative"));
        List<TableColumn<Preview, Double>> columnList = Arrays.asList(ship_value, lease_amount, cumulative);
        for (TableColumn<Preview, Double> tableColumn : columnList){
            tableColumn.setCellFactory(param -> new TableCell<Preview, Double>(){
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) setText(null);
                    else setText(String.format("%.2f", item));
                }
            });
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void initData(T previews) {
        if (previews instanceof ObservableList<?>){
            table_preview.setItems((ObservableList<Preview>) previews);
        }
    }

}
