package org.svseas.model.account;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 11/01/2017, 19:24.
 */

@XmlRootElement
public class CustomerAccount {
    private String username, password, fullName, id;

    public CustomerAccount(String username, String password, String fullName, String id){
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof CustomerAccount) && (Objects.equals(((CustomerAccount) o).getUsername(), this.username));
    }

    @Override
    public int hashCode(){
        int result = 3;
        result = 31 * username.hashCode();
        return result;
    }
}
