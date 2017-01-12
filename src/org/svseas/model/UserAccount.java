package org.svseas.model;

import org.svseas.utils.Access;
import java.util.Objects;

/**
 * Codes by Seong Chee Ken on 11/01/2017, 19:24.
 */
public class UserAccount {
    private String username;
    private String password;
    private Access access;

    public UserAccount(String username, String password, Access access){
        this.username = username;
        this.password = password;
        this.access = access;
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

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof UserAccount) && (Objects.equals(((UserAccount) o).getUsername(), this.username));
    }

    @Override
    public int hashCode(){
        int result = 3;
        result = 31 * username.hashCode();
        return result;
    }
}
