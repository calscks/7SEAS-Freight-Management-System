package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 21/01/2017, 23:44. Client model for table @ client management.
 * Need only getter since data will be set directly to the xml itself.
 */
public class Client extends RecursiveTreeObject<Client> {
    private StringProperty username;
    private StringProperty companyName;
    private StringProperty registry_no;
    private StringProperty phone_no;

    public Client(String username, String companyName, String registry_no, String phone_no) {
        this.username = new SimpleStringProperty(username);
        this.companyName = new SimpleStringProperty(companyName);
        this.registry_no = new SimpleStringProperty(registry_no);
        this.phone_no = new SimpleStringProperty(phone_no);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public String getRegistry_no() {
        return registry_no.get();
    }

    public StringProperty registry_noProperty() {
        return registry_no;
    }

    public String getPhone_no() {
        return phone_no.get();
    }

    public StringProperty phone_noProperty() {
        return phone_no;
    }
}
