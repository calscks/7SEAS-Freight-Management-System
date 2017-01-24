package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 23:48.
 */
public class RouteInTable extends RecursiveTreeObject<RouteInTable> {
    private StringProperty routeId, routeName, routeLength, travel, distance;
    private DoubleProperty ratePerNm, total;

    public RouteInTable(String routeId, String routeName, String routeLength, String travel, String distance,
                        Double ratePerNm, Double total) {
        this.routeId = new SimpleStringProperty(routeId);
        this.routeName = new SimpleStringProperty(routeName);
        this.routeLength = new SimpleStringProperty(routeLength);
        this.travel = new SimpleStringProperty(travel);
        this.distance = new SimpleStringProperty(distance);
        this.ratePerNm = new SimpleDoubleProperty(ratePerNm);
        this.total = new SimpleDoubleProperty(total);
    }

    public String getRouteId() {
        return routeId.get();
    }

    public StringProperty routeIdProperty() {
        return routeId;
    }

    public String getRouteName() {
        return routeName.get();
    }

    public StringProperty routeNameProperty() {
        return routeName;
    }

    public String getRouteLength() {
        return routeLength.get();
    }

    public StringProperty routeLengthProperty() {
        return routeLength;
    }

    public String getTravel() {
        return travel.get();
    }

    public StringProperty travelProperty() {
        return travel;
    }

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public double getRatePerNm() {
        return ratePerNm.get();
    }

    public DoubleProperty ratePerNmProperty() {
        return ratePerNm;
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }
}
