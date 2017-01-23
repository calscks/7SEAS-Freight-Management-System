package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 23:38.
 */
public class PortInTable extends RecursiveTreeObject<PortInTable> {
    private StringProperty portId;
    private StringProperty name;
    private StringProperty dist_nextPort;

    public PortInTable(String portId, String name, String dist_nextPort) {
        this.portId = new SimpleStringProperty(portId);
        this.name = new SimpleStringProperty(name);
        this.dist_nextPort = new SimpleStringProperty(dist_nextPort);
    }

    public String getPortId() {
        return portId.get();
    }

    public StringProperty portIdProperty() {
        return portId;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDist_nextPort() {
        return dist_nextPort.get();
    }

    public StringProperty dist_nextPortProperty() {
        return dist_nextPort;
    }
}
