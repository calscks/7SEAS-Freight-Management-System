package org.svseas.utils.manipulator;

import com.jfoenix.controls.JFXButton;
import org.svseas.model.port.Port;
import org.svseas.operations.PortOperations;

/**
 * Coded by Seong Chee Ken on 24/01/2017, 00:35.
 */
public abstract class PortManipulator extends Manipulator {

    public boolean port_match(Port port){
        PortOperations ops = new PortOperations(port);
        return ops.read(port.getPortId());
    }

    public abstract void manipulate(JFXButton manipulator);

    @Override
    public abstract  <T> void initData(T param);
}
