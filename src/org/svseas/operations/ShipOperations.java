package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.ship.Ship;
import org.svseas.utils.Tester;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:55. CRUD on ship.
 */
public class ShipOperations {
    private XMLOperation xmlops;
    private DataFile df = DataFile.SHIP;
    private Ship ship;

    public ShipOperations(Ship ship) {
        this.ship = ship;
        xmlops = new XMLOperation(ObjectList.class, Ship.class);
    }

    public ShipOperations() {
        xmlops = new XMLOperation(ObjectList.class, Ship.class);
    }

    @SuppressWarnings("unchecked")
    public boolean create(){
        if (!DataFile.analyse(df)) {
            ObjectList<Ship> shipList = new ObjectList<>();
            shipList.add(ship);
            xmlops.write(df, shipList);
            return true;
        }
        ObjectList<Ship> shipList = (ObjectList<Ship>) xmlops.read(df);
        if (shipList.size() == 0){
            shipList.add(ship);
            xmlops.write(df, shipList);
            Tester.SUCCESS.printer();
            return true;
        }
        for (Ship ships : shipList.getList()) {
            if (!ships.equals(ship)) {
                shipList.add(ship);
                xmlops.write(df, shipList);
                Tester.SUCCESS.printer();
                return true;
            }
        }
        Tester.FAIL.printer();
        return false;
    }

    @SuppressWarnings("unchecked")
    public ObjectList<Ship> read(){
        if (DataFile.analyse(df)) {
            ObjectList<Ship> shipList = (ObjectList<Ship>) xmlops.read(df);
            Tester.SUCCESS_READ.printer();
            return shipList;
        } else Tester.FAIL_READ.printer();
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean read(String ship_id){
        if (DataFile.analyse(df)) {
            ObjectList<Ship> shipList = (ObjectList<Ship>) xmlops.read(df);
            for (Ship ship : shipList.getList()){
                if (Objects.equals(ship.getShip_id(), ship_id)) return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void update(String ship_id){
        ObjectList<Ship> shipList = (ObjectList<Ship>) xmlops.read(df);
        for (Ship ships : shipList.getList()){
            if (ships.getShip_id().equals(ship_id)){
                shipList.remove(ships);
                shipList.add(ship);
            }
        }
        xmlops.write(df, shipList);
        Tester.SUCCESS.printer();
    }

    @SuppressWarnings("unchecked")
    public void delete(String ship_id){
        ObjectList<Ship> shipList = (ObjectList<Ship>) xmlops.read(df);
        for (Ship ships : shipList.getList()){
            if (ships.getShip_id().equals(ship_id)) shipList.remove(ships);
        }
        xmlops.write(df, shipList);
        Tester.SUCCESS.printer();
    }
}
