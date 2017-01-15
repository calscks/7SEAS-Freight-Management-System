package org.svseas.data;

/**
 * Codes by Seong Chee Ken on 13/01/2017, 00:09.
 */
public enum DataFile {
    ACCOUNT("useracc", "src/dat/account.xml");

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
}
