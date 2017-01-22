package org.svseas.model.ship;

import org.svseas.data.LeaseType;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:56.
 */
public class Ship {
    private String ship_id, username, type, maxLoad, value, country, amount;
    private LeaseType leaseType;

    public Ship(String ship_id, String username, String type, String maxLoad,
                String value, String country, String amount, LeaseType leaseType) {
        this.ship_id = ship_id;
        this.username = username;
        this.type = type;
        this.maxLoad = maxLoad;
        this.value = value;
        this.country = country;
        this.amount = amount;
        this.leaseType = leaseType;
    }

    public String getShip_id() {
        return ship_id;
    }

    public void setShip_id(String ship_id) {
        this.ship_id = ship_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(String maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public LeaseType getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(LeaseType leaseType) {
        this.leaseType = leaseType;
    }
}
