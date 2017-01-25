package org.svseas.model.freight;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:13.
 */
@XmlRootElement
public class Freight {
    private String bookingId, customer, source, destination, route, travel, cargoWeight, cargoType, totalCharges;

    public Freight(String bookingId, String customer, String source, String destination, String route, String travel,
                   String cargoWeight, String cargoType, String totalCharges) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.source = source;
        this.destination = destination;
        this.route = route;
        this.travel = travel;
        this.cargoWeight = cargoWeight;
        this.cargoType = cargoType;
        this.totalCharges = totalCharges;
    }

    public Freight() {
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(String cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public String getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(String totalCharges) {
        this.totalCharges = totalCharges;
    }
}
