package org.svseas.data;

import java.io.File;

/**
 * Coded by Seong Chee Ken on 13/01/2017, 00:09.
 */

//TODO: Show during presentation - usage of enums
public enum DataFile {
    CUSTOMER("Customer", "dat/customer.xml"),
    ADMIN("Admin", "dat/admin.xml"),
    CLIENT("Client", "dat/client.xml"),
    SHIP("Ship", "dat/ship.xml"),
    PORT("Port", "dat/port.xml"),
    ROUTE("Route", "dat/route.xml"),
    ROUTE_SHIP("RouteShip", "dat/routeship.xml"),
    FREIGHT("Freight", "dat/freight.xml");


    private String data_name;
    private String data_path;

    DataFile(String data_name, String data_path){
        this.data_name = data_name;
        this.data_path = data_path;
    }

    public String getData_name() {
        return data_name;
    }

    public String getData_path() {
        return data_path;
    }

    public static boolean analyse(DataFile dataFile){
        File file = new File(dataFile.getData_path());
        return file.isFile();
    }
}
