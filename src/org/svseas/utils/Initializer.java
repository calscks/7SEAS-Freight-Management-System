package org.svseas.utils;

import org.svseas.data.DataFile;
import org.svseas.model.account.AdminAccount;
import org.svseas.model.ObjectList;
import org.svseas.operations.XMLOperation;

import java.io.File;

/**
 * Coded by Seong Chee Ken on 08/01/2017, 01:25.
 */
public class Initializer {
    public Initializer() {
        File dir = new File("dat");
        boolean isDirCreated = dir.mkdir();
        if (isDirCreated){
            System.out.println("dat dir created");
        } else {
            System.out.println("dat dir not created");
        }
        //creates admin account
        if (!DataFile.analyse(DataFile.ADMIN)) {
            AdminAccount admin = new AdminAccount("admin", "admin", "Administrator");
            ObjectList<AdminAccount> adminList = new ObjectList<>();
            adminList.add(admin);
            new XMLOperation(ObjectList.class, AdminAccount.class).write(DataFile.ADMIN, adminList);
        }
    }
}
