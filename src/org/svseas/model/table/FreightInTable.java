package org.svseas.model.table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:45.
 */
public class FreightInTable {
    private StringProperty bookingId, customer, source, destination, route, travel, cargoWeight, cargoType;
    private DoubleProperty totalCharges;

    public FreightInTable(String bookingId, String customer, String source, String destination, String route,
                          String travel, String cargoWeight, String cargoType, Double totalCharges) {
        this.bookingId = new SimpleStringProperty(bookingId);
        this.customer = new SimpleStringProperty(customer);
        this.source = new SimpleStringProperty(source);
        this.destination = new SimpleStringProperty(destination);
        this.route = new SimpleStringProperty(route);
        this.travel = new SimpleStringProperty(travel);
        this.cargoWeight = new SimpleStringProperty(cargoWeight);
        this.cargoType = new SimpleStringProperty(cargoType);
        this.totalCharges = new SimpleDoubleProperty(totalCharges);
    }

    public String getBookingId() {
        return bookingId.get();
    }

    public StringProperty bookingIdProperty() {
        return bookingId;
    }

    public String getCustomer() {
        return customer.get();
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public String getSource() {
        return source.get();
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public String getRoute() {
        return route.get();
    }

    public StringProperty routeProperty() {
        return route;
    }

    public String getTravel() {
        return travel.get();
    }

    public StringProperty travelProperty() {
        return travel;
    }

    public String getCargoWeight() {
        return cargoWeight.get();
    }

    public StringProperty cargoWeightProperty() {
        return cargoWeight;
    }

    public String getCargoType() {
        return cargoType.get();
    }

    public StringProperty cargoTypeProperty() {
        return cargoType;
    }

    public double getTotalCharges() {
        return totalCharges.get();
    }

    public DoubleProperty totalChargesProperty() {
        return totalCharges;
    }
}
