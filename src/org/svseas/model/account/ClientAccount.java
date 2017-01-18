package org.svseas.model.account;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 17/01/2017, 23:34.
 */

@XmlRootElement
public class ClientAccount extends Account {
    private String companyName, registryNo, phoneNo;

    public ClientAccount(String username, String password, String companyName, String registryNo, String phoneNo) {
        super(username, password);
        this.companyName = companyName;
        this.registryNo = registryNo;
        this.phoneNo = phoneNo;
    }

    public ClientAccount(){}

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

    @Override
    public boolean equals(Object o) {
        return (o instanceof ClientAccount && (Objects.equals(((ClientAccount) o).getUsername(), super.getUsername())));
    }

    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * super.getUsername().hashCode();
        return result;
    }
}
