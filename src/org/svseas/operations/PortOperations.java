package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.utils.Tester;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 23:42. CRUD on ports.
 */
@SuppressWarnings("unchecked")
public class PortOperations extends Operation {
    private XMLOperation xmlops;
    private DataFile df = DataFile.PORT;
    private Port port;

    public PortOperations(Port port) {
        this.port = port;
        xmlops = new XMLOperation(ObjectList.class, Port.class);
    }

    public PortOperations() {
        xmlops = new XMLOperation(ObjectList.class, Port.class);
    }

    public boolean create(){
        return super.create(port, xmlops, df);
    }

    public ObjectList<Port> read(){
        return super.read(df, xmlops);
    }

    public boolean read(String port_id){
        if (DataFile.analyse(df)) {
            ObjectList<Port> shipList = (ObjectList<Port>) xmlops.read(df);
            for (Port port : shipList.getList()){
                if (Objects.equals(port.getPortId(), port_id)) return true;
            }
        }
        return false;
    }

    @Override
    public void update(String port_id) {
        ObjectList<Port> portList = (ObjectList<Port>) xmlops.read(df);
        for (Port ports : portList.getList()){
            if (ports.getPortId().equals(port_id)){
                portList.remove(ports);
                portList.add(port);
            }
        }
        xmlops.write(df, portList);
        Tester.SUCCESS.printer();
    }

    @Override
    public boolean delete(String port_id) {
        ObjectList<Port> portList = (ObjectList<Port>) xmlops.read(df);
        for (Port ports : portList.getList()){
            if (ports.getPortId().equals(port_id)) portList.remove(ports);
        }
        xmlops.write(df, portList);
        Tester.SUCCESS.printer();
        return true;
    }
}
