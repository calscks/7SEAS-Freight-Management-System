package org.svseas.utils;

import org.svseas.data.DataFile;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.model.account.AdminAccount;

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
        AdminAccount adminAccount = new AdminAccount("admin", "admin", "Administrator");
        AccountOperations<AdminAccount> ops = new AccountOperations<>(adminAccount, DataFile.ADMIN);
        ops.create();
    }
}
