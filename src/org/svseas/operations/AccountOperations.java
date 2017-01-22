package org.svseas.operations;

import javafx.concurrent.Task;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.Account;
import org.svseas.model.account.AdminAccount;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.account.CustomerAccount;
import org.svseas.utils.Tester;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 15:58. Proper CRUD operations for customer, client and admin.
 * Mostly CRUD operations for customer and client.
 */
public class AccountOperations<T extends Account> {
    private XMLOperation xmlops;
    private Account account;
    private DataFile df;

    public AccountOperations(Account account, DataFile df) {
        this.account = account;
        this.df = df;
        if (account instanceof AdminAccount) xmlops = new XMLOperation(ObjectList.class, AdminAccount.class);
        else if (account instanceof CustomerAccount) xmlops = new XMLOperation(ObjectList.class, CustomerAccount.class);
        else if (account instanceof ClientAccount) xmlops = new XMLOperation(ObjectList.class, ClientAccount.class);
    }

    public AccountOperations(DataFile df) {
        this.df = df;
        if (df == DataFile.ADMIN) xmlops = new XMLOperation(ObjectList.class, AdminAccount.class);
        else if (df == DataFile.CUSTOMER) xmlops = new XMLOperation(ObjectList.class, CustomerAccount.class);
        else if (df == DataFile.CLIENT) xmlops = new XMLOperation(ObjectList.class, ClientAccount.class);
    }

    @SuppressWarnings("unchecked")
    public boolean create() {
        if (!DataFile.analyse(df)) {
            ObjectList<T> accountList = new ObjectList<>();
            accountList.add((T) account);
            xmlops.write(df, accountList);
            Tester.SUCCESS.printer();
            return true;
        }
        ObjectList<T> accountList = (ObjectList<T>) xmlops.read(df);
        if (accountList.size() == 0){
            accountList.add((T) account);
            xmlops.write(df, accountList);
            Tester.SUCCESS.printer();
            return true;
        }
        for (T admin : accountList.getList()) {
            if (!admin.equals(account)) {
                accountList.add((T) account);
                xmlops.write(df, accountList);
                Tester.SUCCESS.printer();
                return true;
            }
        }
        Tester.FAIL.printer();
        return false;
    }

    //for login match
    @SuppressWarnings("unchecked")
    public boolean read(String username, String password) {
        ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
        for (T account : list.getList()) {
            if (account.getUsername().equals(username) && account
                    .getPassword().equals(password)) {
                Tester.SUCCESS_MATCH.printer();
                return true;
            }
        }
        Tester.FAIL_MATCH.printer();
        return false;
    }

    //validation match
    @SuppressWarnings("unchecked")
    public boolean read(String username) {
        ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
        for (T account : list.getList()) {
            if (account.getUsername().equals(username)) {
                Tester.SUCCESS_MATCH.printer();
                return true;
            }
        }
        Tester.FAIL_MATCH.printer();
        return false;
    }

    //normal read
    @SuppressWarnings("unchecked")
    public ObjectList<T> read() {
        if (DataFile.analyse(df)) {
            ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
            Tester.SUCCESS_READ.printer();
            return list;
        } else Tester.FAIL_READ.printer();
        return null;
    }

    //admin is not necessary anymore, update is done for client and customer only
    @SuppressWarnings("unchecked")
    public boolean update(String username) {
        ObjectList<T> accountList = (ObjectList<T>) xmlops.read(df);
        for (Account accounts : accountList.getList()) {
            if (accounts.getUsername().equals(username)) {
                accountList.remove((T) accounts);
                accountList.add((T) account);
            }
        }
        xmlops.write(df, accountList);
        Tester.SUCCESS.printer();
        return false;
    }

    //MY PROGRAM IS FORCING ME TO DO MULTI-THREADING NOW OR ELSE IT'LL THROW CONCURRENCY EXCEPTION
    @SuppressWarnings("unchecked")
    public boolean delete(String username) {
        ObjectList<T> accountList = (ObjectList<T>) xmlops.read(df);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (T account : accountList.getList()) {
                    if (account instanceof CustomerAccount) {
                        if (account.getUsername().equals(username)) {
                            accountList.remove(account);
                            Tester.SUCCESS.printer();
                        }
                    } else if (account instanceof ClientAccount) {
                        if (account.getUsername().equals(username)) {
                            accountList.remove(account);
                            Tester.SUCCESS.printer();
                        }
                    }
                }
                return null;
            }
        };
        task.run();
        xmlops.write(df, accountList);
        return true;
    }
}
