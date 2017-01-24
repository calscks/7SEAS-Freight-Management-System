package org.svseas.operations;

import javafx.concurrent.Task;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.utils.Tester;

import java.util.Iterator;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class RouteOperations extends Operation {

    private XMLOperation xmlops;
    private Route<Port> route;
    private DataFile df = DataFile.ROUTE;

    public RouteOperations(Route<Port> route) {
        this.route = route;
        xmlops = new XMLOperation(ObjectList.class, Route.class, Port.class);
    }

    public RouteOperations() {
        xmlops = new XMLOperation(ObjectList.class, Route.class, Port.class);
    }

    public boolean create() {
        return super.create(route, xmlops, df);
    }

    public ObjectList<Route<Port>> read() {
        return super.read(df, xmlops);
    }

    public boolean read(String route_id){
        if (DataFile.analyse(df)){
            ObjectList<Route<Port>> routeList = (ObjectList<Route<Port>>) xmlops.read(df);
            for (Route<Port> route : routeList.getList()){
                if (Objects.equals(route.getRouteId(), route_id)) return true;
            }
        } return false;
    }

    @Override
    public void update(String route_id) {
        ObjectList<Route<Port>> routeList = (ObjectList<Route<Port>>) xmlops.read(df);

        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Route<Port> routes : routeList.getList()) {
                    if (Objects.equals(routes.getRouteId(), route_id)) {
                        int index = routeList.getList().indexOf(routes);
                        routeList.remove(routes);
                        routeList.add(index, route);
                        xmlops.write(df, routeList);
                    }
                }
                return null;
            }
        }.run();
        Tester.SUCCESS.printer();
    }

    @Override
    public boolean delete(String route_id) {
        ObjectList<Route<Port>> routeList = (ObjectList<Route<Port>>) xmlops.read(df);
        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Route<Port> routes : routeList.getList()){
                    if (routes.getRouteId().equals(route_id)){
                        routeList.remove(routes);
                        xmlops.write(df, routeList);
                    }
                }
                return null;
            }
        }.run();
        Tester.SUCCESS.printer();
        return true;
    }
}
