package org.svseas.model.table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 12:28.
 */
public class Preview{
    private IntegerProperty year;
    private DoubleProperty ship_value;
    private DoubleProperty payment;
    private DoubleProperty cumulative;

    public Preview(int year, double ship_value, double payment, double cumulative) {
        this.year = new SimpleIntegerProperty(year);
        this.ship_value = new SimpleDoubleProperty(ship_value);
        this.payment = new SimpleDoubleProperty(payment);
        this.cumulative = new SimpleDoubleProperty(cumulative);
    }

    public double getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public double getShip_value() {
        return ship_value.get();
    }

    public DoubleProperty ship_valueProperty() {
        return ship_value;
    }

    public double getPayment() {
        return payment.get();
    }

    public DoubleProperty paymentProperty() {
        return payment;
    }

    public double getCumulative() {
        return cumulative.get();
    }

    public DoubleProperty cumulativeProperty() {
        return cumulative;
    }
}
