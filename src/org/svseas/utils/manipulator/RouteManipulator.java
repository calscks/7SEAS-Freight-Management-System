package org.svseas.utils.manipulator;

import org.svseas.model.route.Port;
import org.svseas.model.route.Route;
import org.svseas.operations.RouteOperations;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 11:43.
 */
public abstract class RouteManipulator extends Manipulator {

    protected boolean route_match(Route<Port> route){
        RouteOperations ops = new RouteOperations();
        return ops.read(route.getRouteId());
    }

    @Override
    public abstract  <T> void initData(T param);
}
