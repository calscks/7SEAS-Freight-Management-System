package org.svseas.model;

import org.svseas.data.UserType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Codes by Seong Chee Ken on 11/01/2017, 19:24.
 */

@XmlRootElement(name = "useracc")
public class UserAccount {
    private String username;
    private String password;
    private UserType userType;

    public UserAccount(String username, String password, UserType userType){
        this.username = username;
        this.password = password;
        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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
