package org.svseas.utils.manipulator;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.CustomerAccount;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.operations.AccountOperations;
import org.svseas.operations.RouteOperations;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:13.
 */
@SuppressWarnings("unchecked")
public abstract class FreightManipulator extends RouteManipulator {

    protected void loadCustomerId(JFXComboBox<String> customer){
        AccountOperations accops = new AccountOperations(DataFile.CUSTOMER);
        ObjectList<CustomerAccount> custList = (ObjectList<CustomerAccount>) accops.read();
        ObservableList<String> usernameList = FXCollections.observableArrayList();

        for (CustomerAccount account : custList.getList()){
            usernameList.add(account.getUsername());
        }

        customer.setItems(usernameList);
    }

    @Override
    protected void loadPort(JFXComboBox<String> source, JFXComboBox<String> dest) {
        super.loadPort(source, dest);
    }

    //enable loading the routes, source & dest listeners
    protected void enableLoadRoute(JFXComboBox route, JFXComboBox source, JFXComboBox dest){
        BooleanBinding binding = source.getSelectionModel().selectedItemProperty().isNull()
                .or(dest.getSelectionModel().selectedItemProperty().isNull());
        route.selectionModelProperty().bind(binding);

        source.getSelectionModel().selectedItemProperty().addListener(listener(route, source, dest));
        dest.getSelectionModel().selectedItemProperty().addListener(listener(route, source, dest));
    }

    private ChangeListener listener(JFXComboBox routeBox, JFXComboBox source, JFXComboBox dest){
        RouteOperations ops = new RouteOperations();
        return (observable, oldValue, newValue) -> {
            String start = (String) source.getSelectionModel().getSelectedItem();
            String end = (String) dest.getSelectionModel().getSelectedItem();
            ObservableList<String> routeNames = FXCollections.observableArrayList();
            if (start != null && end != null){
                ObjectList<Route<Port>> routeList = ops.read();
                for (Route<Port> route : routeList.getList()){
                    if (Objects.equals(start, route.get(0).getName()) && Objects.equals(end, route.getLast().getName())){
                        routeNames.add(route.getRouteName());
                    }
                }
                routeBox.setItems(routeNames);
            }
        };
    }

    @Override
    public abstract  <T> void initData(T param);
}
