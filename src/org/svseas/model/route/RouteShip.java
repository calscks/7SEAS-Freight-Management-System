package org.svseas.model.route;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 01:53.
 */
@XmlRootElement
public class RouteShip {
    private String ship_id, route_name;

    public RouteShip(String ship_id, String route_name) {
        this.ship_id = ship_id;
        this.route_name = route_name;
    }

    public RouteShip() {
    }

    public String getShip_id() {
        return ship_id;
    }

    public void setShip_id(String ship_id) {
        this.ship_id = ship_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }
}
