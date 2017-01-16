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
    public Initializer() {
        //creates admin account
        if (!AccountOperations.analyse()) {
            UserAccount userAccount = new UserAccount("admin", "admin", UserType.ADMIN);
            ObjectList<UserAccount> adminList = new ObjectList<>();
            adminList.add(userAccount);
            new XMLOperation(ObjectList.class, UserAccount.class).write(DataFile.ACCOUNT, adminList);
            UserAccount userAccount2 = new UserAccount("admin2", "admin2", UserType.ADMIN);
            ObjectList<UserAccount> adminList2 = new ObjectList<>();
            adminList2.add(userAccount2);
            new XMLOperation(ObjectList.class, UserAccount.class).write(DataFile.ACCOUNT, adminList2);
        }
    }
}
