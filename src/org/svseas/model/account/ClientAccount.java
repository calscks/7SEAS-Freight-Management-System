package org.svseas.model.account;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Coded by Seong Chee Ken on 17/01/2017, 23:34.
 */

@XmlRootElement
public class ClientAccount {
    private String username, password, companyName, registryNo, phoneNo;

    public ClientAccount(String username, String password, String companyName, String registryNo, String phoneNo) {
        this.username = username;
        this.password = password;
        this.companyName = companyName;
        this.registryNo = registryNo;
        this.phoneNo = phoneNo;
    }

    public ClientAccount(){}

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRegistryNo() {
        return registryNo;
    }

    public void setRegistryNo(String registryNo) {
        this.registryNo = registryNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
