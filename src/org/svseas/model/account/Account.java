package org.svseas.model.account;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 15:59.
 */
public class Account {
    private String username, password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(){}

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

    @Override
    public boolean equals(Object o) {
        return (o instanceof Account) && (Objects.equals(((Account) o).getUsername(), this.username));
    }

    @Override
    public int hashCode(){
        int result = 3;
        result = 31 * username.hashCode();
        return result;
    }
}
