package org.svseas.model.account;

import org.svseas.model.account.Account;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 16:08.
 */
@XmlRootElement
public class AdminAccount extends Account {
    private String fullName;
    public AdminAccount(String username, String password, String fullName) {
        super(username, password);
        this.fullName = fullName;
    }

    public AdminAccount(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof AdminAccount && (Objects.equals(((AdminAccount) o).getUsername(), super.getUsername())));
    }

    @Override
    public int hashCode() {
        int result = 3;
        result = 31 * super.getUsername().hashCode();
        return result;
    }
}
