package org.svseas.model.ship;

import org.svseas.data.LeaseType;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:56.
 */
@XmlRootElement
public class Ship {
    private String ship_id, username, type, maxLoad, value, country, period;
    private LeaseType leaseType;

    public Ship(String ship_id, String username, String type, String maxLoad,
                String value, String country, String period, LeaseType leaseType) {
        this.ship_id = ship_id;
        this.username = username;
        this.type = type;
        this.maxLoad = maxLoad;
        this.value = value;
        this.country = country;
        this.period = period;
        this.leaseType = leaseType;
    }

    public Ship() {
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LeaseType getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(LeaseType leaseType) {
        this.leaseType = leaseType;
    }

    //for ship inside combo box
    @Override
    public String toString() {
        return type + " - " + ship_id;
    }
}
