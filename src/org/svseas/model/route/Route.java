package org.svseas.model.route;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 11:45.
 */
@XmlRootElement
public class Route <T> {
    private String routeId, routeName, routeLength, ratePerNm, total;
    private List<T> portList;


    public Route(String routeId, String routeName, String routeLength, String ratePerNm, String total) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.routeLength = routeLength;
        this.ratePerNm = ratePerNm;
        this.total = total;
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

    public void remove(T t){
        portList.remove(t);
    }

}
