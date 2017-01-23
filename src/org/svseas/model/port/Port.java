package org.svseas.model.port;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 23:10.
 */
@XmlRootElement
public class Port {
    private String portId;
    private String name;
    private String distance_nextPort;

    public Port(String portId, String name, String distance_nextPort) {
        this.portId = portId;
        this.name = name;
        this.distance_nextPort = distance_nextPort;
    }

    public Port() {
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance_nextPort() {
        return distance_nextPort;
    }

    public void setDistance_nextPort(String distance_nextPort) {
        this.distance_nextPort = distance_nextPort;
    }
}
