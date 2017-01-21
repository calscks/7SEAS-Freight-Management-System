package org.svseas.model.table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Coded by Seong Chee Ken on 21/01/2017, 12:36. Customer model for table @ customer management.
 * Needs only getter since data is set directly to the xml itself.
 */
public class Customer extends RecursiveTreeObject<Customer>{
    private StringProperty username;
    private StringProperty fullName;
    private StringProperty id_no;

    public Customer(String username, String fullName, String id_no) {
        this.username = new SimpleStringProperty(username);
        this.fullName = new SimpleStringProperty(fullName);
        this.id_no = new SimpleStringProperty (id_no);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }


    public StringProperty id_noProperty() {
        return id_no;
    }

    public String getUsername() {
        return username.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getId_no() {
        return id_no.get();
    }
}
