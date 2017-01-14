package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.data.UserType;
import org.svseas.model.ObjectList;
import org.svseas.model.UserAccount;

import javax.xml.bind.JAXBException;

/**
 * Codes by Seong Chee Ken on 14/01/2017, 18:05. CRUD operations on Account.
 */
public class AccountOperations {

    XMLOperation xmlOperation = new XMLOperation(ObjectList.class, UserAccount.class);

    public AccountOperations() throws JAXBException {
    }

    public boolean create(String username, String password, UserType userType){
        UserAccount userAccount = new UserAccount(username, password, userType);
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (!account.equals(userAccount)){
                accountList.add(userAccount);
                xmlOperation.write(DataFile.ACCOUNT, accountList);
                return true;
            }
        } return false;
    }

    public boolean read(String username){
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (account.getUsername().equals(username)) return true;
        } return false;
    }

    public boolean update(UserAccount userAccount){
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (account.equals(userAccount)){
                accountList.remove(account);
                accountList.add(userAccount);
                return true;
            }
        } return false;
    }

    public boolean remove(String username){
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (account.getUsername().equals(username)){
                accountList.remove(account);
                return true;
            }
        } return false;
    }
}
