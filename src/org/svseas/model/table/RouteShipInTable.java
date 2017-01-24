package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 01:53.
 */
public class RouteShipInTable extends RecursiveTreeObject<RouteShipInTable> {
    private StringProperty ship_id, route_name;

    public RouteShipInTable(String ship_id, String route_name) {
        this.ship_id = new SimpleStringProperty(ship_id);
        this.route_name = new SimpleStringProperty(route_name);
    }

    public String getShip_id() {
        return ship_id.get();
    }

    public StringProperty ship_idProperty() {
        return ship_id;
    }

    public String getRoute_name() {
        return route_name.get();
    }

    public StringProperty route_nameProperty() {
        return route_name;
    }
}
