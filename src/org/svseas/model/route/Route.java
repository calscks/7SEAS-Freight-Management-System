package org.svseas.model.route;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 11:45.
 */
@XmlRootElement
public class Route <T> {
    private String routeId, routeName, routeLength, ratePerNm, total, travel, distance;
    private List<T> portList;

    public Route(String routeId, String routeName, String routeLength, String ratePerNm, String total, String travel,
                 String distance) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.routeLength = routeLength;
        this.ratePerNm = ratePerNm;
        this.total = total;
        this.travel = travel;
        this.distance = distance;
    }

    public Route() {
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(String routeLength) {
        this.routeLength = routeLength;
    }

    public String getRatePerNm() {
        return ratePerNm;
    }

    public void setRatePerNm(String ratePerNm) {
        this.ratePerNm = ratePerNm;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @XmlAnyElement (lax=true)

    public List<T> getPortList() {
        return portList;
    }

    public void setPortList(List<T> portList) {
        this.portList = portList;
    }

    public void add(T t){
        portList.add(t);
    }

    public int size(){
        return portList.size();
    }

    public T get(int index){
        return portList.get(index);
    }

    public T getLast(){
        return portList.get(portList.size() - 1);
    }

    public void remove(T t){
        portList.remove(t);
    }


}
