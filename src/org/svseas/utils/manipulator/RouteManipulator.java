package org.svseas.utils.manipulator;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 11:43.
 */
public abstract class RouteManipulator extends Manipulator {

    protected boolean route_match(){
        return false;
    }

    @Override
    public abstract  <T> void initData(T param);
}
