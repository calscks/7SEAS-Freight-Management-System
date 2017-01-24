package org.svseas.operations;

import javafx.concurrent.Task;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.utils.Tester;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class RouteOperations extends Operation {

    private XMLOperation xmlops;
    private Route<Port> route;
    private DataFile df = DataFile.ROUTE;

    public RouteOperations(Route<Port> route) {
        this.route = route;
        xmlops = new XMLOperation(ObjectList.class, Route.class);
    }

    public RouteOperations() {
        xmlops = new XMLOperation(ObjectList.class, Route.class);
    }

    @Override
    public <T> boolean create(T type, XMLOperation xmlops, DataFile df) {
        return super.create(type, xmlops, df);
    }

    @Override
    public <T> ObjectList<T> read(DataFile df, XMLOperation xmlops) {
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
        for (Route<Port> route : routeList.getList()){
            if (Objects.equals(route.getRouteId(), route_id)){
                routeList.remove(route);
                routeList.add(this.route);
            }
        }
        xmlops.write(df, routeList);
        Tester.SUCCESS.printer();
    }

    @Override
    public boolean delete(String route_id) {
        ObjectList<Route<Port>> routeList = (ObjectList<Route<Port>>) xmlops.read(df);
        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Route<Port> routes : routeList.getList()){
                    if (routes.getRouteId().equals(route_id)) routeList.remove(routes);
                }
                return null;
            }
        }.run();
        xmlops.write(df, routeList);
        Tester.SUCCESS.printer();
        return true;
    }
}
