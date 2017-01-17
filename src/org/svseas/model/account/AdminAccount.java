package org.svseas.model.account;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 00:01.
 */
@XmlRootElement
public class AdminAccount {
    private String username, password, fullName;

    public AdminAccount(String username, String password, String fullName){
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public AdminAccount(){}

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
