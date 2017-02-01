package org.svseas.model.account;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 11/01/2017, 19:24.
 */

//TODO: Show during presentation - ENCAPSULATION and SUBTYPE POLYMORPHISM

@XmlRootElement
public class CustomerAccount extends Account{
    private String fullName, id;

    public CustomerAccount(String username, String password, String fullName, String id) {
        super(username, password);
        this.fullName = fullName;
        this.id = id;
    }

    public CustomerAccount(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof CustomerAccount) && (Objects.equals(((CustomerAccount) o).getUsername(), super.getUsername()));
    }

    //TODO: Show during presentation - METHOD OVERRIDING
    @Override
    public int hashCode(){
        int result = 3;
        result = 31 * super.getUsername().hashCode();
        return result;
    }
}
