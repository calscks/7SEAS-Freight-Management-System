package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.Account;
import org.svseas.model.account.AdminAccount;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.account.CustomerAccount;
import org.svseas.utils.Tester;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 15:58. Proper CRUD operations for customer, client and admin.
 */
public class AccountOperations<T> {
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

    //for login
    @SuppressWarnings("unchecked")
    public boolean read(String username, String password) {
        ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
        for (T account : list.getList()) {
            if (account instanceof AdminAccount) {
                if (((AdminAccount) account).getUsername().equals(username) && ((AdminAccount) account)
                        .getPassword().equals(password)) {
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            } else if (account instanceof CustomerAccount) {
                if (((CustomerAccount) account).getUsername().equals(username) && ((CustomerAccount) account)
                        .getPassword().equals(password)) {
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            } else if (account instanceof ClientAccount) {
                if (((ClientAccount) account).getUsername().equals(username) && ((ClientAccount) account)
                        .getPassword().equals(password)) {
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            }
        }
        Tester.FAIL_MATCH.printer();
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean read(String username) {
        ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
        for (T account : list.getList()) {
            if (account instanceof AdminAccount) {
                if (((AdminAccount) account).getUsername().equals(username)){
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            } else if (account instanceof CustomerAccount) {
                if (((CustomerAccount) account).getUsername().equals(username)) {
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            } else if (account instanceof ClientAccount) {
                if (((ClientAccount) account).getUsername().equals(username)) {
                    Tester.SUCCESS_MATCH.printer();
                    return true;
                }
            }
        }
        Tester.FAIL_MATCH.printer();
        return false;
    }

    //admin is not necessary anymore, update is done for client and customer only
    @SuppressWarnings("unchecked")
    public boolean update(Account account) {
        ObjectList<T> accountList = (ObjectList<T>) xmlops.read(df);
        for (T accounts : accountList.getList()) {
            if (accounts.equals(account)) {
                accountList.remove(accounts);
                accountList.add((T) account);
                Tester.SUCCESS.printer();
                return true;
            }
        }
        Tester.FAIL.printer();
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean delete(String username) {
        ObjectList<T> accountList = (ObjectList<T>) xmlops.read(df);
        for (T account : accountList.getList()) {
            if (account instanceof CustomerAccount) {
                if (((CustomerAccount) account).getUsername().equals(username)) {
                    accountList.remove(account);
                    Tester.SUCCESS.printer();
                    return true;
                }
            }
        }
        Tester.FAIL.printer();
        return false;
    }
}
