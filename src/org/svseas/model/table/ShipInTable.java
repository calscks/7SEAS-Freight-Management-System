package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 19:42.
 */
public class ShipInTable extends RecursiveTreeObject<ShipInTable>{
    private StringProperty ship_id, username, type, maxLoad, value, country, period, leaseType;

    public ShipInTable(String ship_id, String username, String type, String maxLoad, String value, String country, String period, String leaseType) {
        this.ship_id = new SimpleStringProperty(ship_id);
        this.username = new SimpleStringProperty(username);
        this.type = new SimpleStringProperty(type);
        this.maxLoad = new SimpleStringProperty(maxLoad);
        this.value = new SimpleStringProperty(value);
        this.country = new SimpleStringProperty(country);
        this.period = new SimpleStringProperty(period);
        this.leaseType = new SimpleStringProperty(leaseType);
    }

    public String getShip_id() {
        return ship_id.get();
    }

    public StringProperty ship_idProperty() {
        return ship_id;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getMaxLoad() {
        return maxLoad.get();
    }

    public StringProperty maxLoadProperty() {
        return maxLoad;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getPeriod() {
        return period.get();
    }

    public StringProperty periodProperty() {
        return period;
    }

    public String getLeaseType() {
        return leaseType.get();
    }

    public StringProperty leaseTypeProperty() {
        return leaseType;
    }
}
