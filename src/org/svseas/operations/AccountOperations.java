package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.data.UserType;
import org.svseas.model.ObjectList;
import org.svseas.model.UserAccount;
import org.svseas.utils.Tester;

import javax.xml.bind.JAXBException;
import java.net.URL;

/**
 * Codes by Seong Chee Ken on 14/01/2017, 18:05. CRUD operations on Account.
 */
public class AccountOperations {

    private static XMLOperation xmlOperation = new XMLOperation(ObjectList.class, UserAccount.class);

    public AccountOperations() throws JAXBException {
    }

    public static boolean analyse(){
        URL url = AccountOperations.class.getClassLoader().getResource(DataFile.ACCOUNT.getData_path());
        return url != null; //if url != null, true, else false
    }

    public static boolean create(String username, String password, UserType userType){
        UserAccount userAccount = new UserAccount(username, password, userType);
        if (!analyse()){
            return false;
        }
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

    public static boolean read(String username){
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (account.getUsername().equals(username)) {
                Tester.SUCCESS.printer();
                return true;
            }
        } Tester.FAIL.printer();
        return false;
    }

    public static boolean read(String username, String password){
        @SuppressWarnings("unchecked")
        ObjectList<UserAccount> accountList = (ObjectList<UserAccount>) xmlOperation.read(DataFile.ACCOUNT);
        for (UserAccount account: accountList.getList()){
            if (account.getUsername().equals(username) && account.getPassword().equals(password))
            {
                Tester.SUCCESS.printer();
                return true;
            }
        } Tester.FAIL.printer();
        return false;
    }

    public static boolean update(UserAccount userAccount){
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

    public static boolean remove(String username){
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
