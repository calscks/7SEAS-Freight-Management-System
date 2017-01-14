package org.svseas.utils;

import org.svseas.data.DataFile;
import org.svseas.data.UserType;
import org.svseas.model.ObjectList;
import org.svseas.model.UserAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.operations.XMLOperation;

import javax.xml.bind.JAXBException;

/**
 * Codes by Seong Chee Ken on 08/01/2017, 01:25.
 */
public class Initializer {
    public Initializer(){
        //creates admin account
        try {
            if (!new AccountOperations().analyse()){
                UserAccount userAccount = new UserAccount("admin","admin", UserType.ADMIN);
                ObjectList<UserAccount> adminList = new ObjectList<>();
                adminList.add(userAccount);
                new XMLOperation(ObjectList.class, UserAccount.class).write(DataFile.ACCOUNT, adminList);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
