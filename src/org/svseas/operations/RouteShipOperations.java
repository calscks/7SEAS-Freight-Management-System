package org.svseas.operations;

import javafx.concurrent.Task;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.RouteShip;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 01:57. ONLY CRD, NO U
 */
@SuppressWarnings("unchecked")
public class RouteShipOperations extends Operation {

    private XMLOperation xmlops;
    private DataFile df = DataFile.ROUTE_SHIP;
    private RouteShip routeShip;

    public RouteShipOperations(RouteShip routeShip) {
        this.routeShip = routeShip;
        xmlops = new XMLOperation(ObjectList.class, RouteShip.class);
    }

    public RouteShipOperations() {
        xmlops = new XMLOperation(ObjectList.class, RouteShip.class);
    }

    public boolean create() {
        return super.create(routeShip, xmlops, df);
    }

    public ObjectList<RouteShip> read() {
        return super.read(df, xmlops);
    }

    public boolean read(String ship_id, String route_name){
        if (DataFile.analyse(df)) {
            ObjectList<RouteShip> shipList = (ObjectList<RouteShip>) xmlops.read(df);
            for (RouteShip routeShip : shipList.getList()){
                if (Objects.equals(routeShip.getShip_id(), ship_id) &&
                        Objects.equals(routeShip.getRoute_name(), route_name)) return true;
            }
        }
        return false;
    }

    @Override
    public void update(String match) {}

    @Override
    public boolean delete(String match) {
        return false;
    }

    public boolean delete(String ship_id, String route_name) {
        if (DataFile.analyse(df)) {
            ObjectList<RouteShip> shipList = (ObjectList<RouteShip>) xmlops.read(df);
            new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    for (RouteShip routeShip : shipList.getList()){
                        if (routeShip.getShip_id().equals(ship_id) && routeShip.getRoute_name().equals(route_name))
                            shipList.remove(routeShip);
                        xmlops.write(df, shipList);
                    }
                    return null;
                }
            }.run();
        }
        return false;
    }
}
