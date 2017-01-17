package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.ClientAccount;
import org.svseas.utils.Tester;

/**
 * Coded by Seong Chee Ken on 18/01/2017, 00:52.
 */
public class ClientAccOperations {

    private static XMLOperation xmlOperation = new XMLOperation(ObjectList.class, ClientAccount.class);
    
    public static boolean create(ClientAccount clientAccount){
        if (!DataFile.analyse(DataFile.CLIENT)){
            return false;
        }
        @SuppressWarnings("unchecked")
        ObjectList<ClientAccount> accountList = (ObjectList<ClientAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (ClientAccount account: accountList.getList()){
            if (!account.equals(clientAccount)){
                accountList.add(clientAccount);
                xmlOperation.write(DataFile.CUSTOMER, accountList);
                return true;
            }
        } return false;
    }

    public static boolean read(String username, String password){
        @SuppressWarnings("unchecked")
        ObjectList<ClientAccount> accountList = (ObjectList<ClientAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (ClientAccount account: accountList.getList()){
            if (account.getUsername().equals(username) && account.getPassword().equals(password))
            {
                Tester.SUCCESS.printer();
                return true;
            }
        } Tester.FAIL.printer();
        return false;
    }

    public static boolean update(ClientAccount ClientAccount){
        @SuppressWarnings("unchecked")
        ObjectList<ClientAccount> accountList = (ObjectList<ClientAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (ClientAccount account: accountList.getList()){
            if (account.equals(ClientAccount)){
                accountList.remove(account);
                accountList.add(ClientAccount);
                return true;
            }
        } return false;
    }

    public static boolean remove(String username){
        @SuppressWarnings("unchecked")
        ObjectList<ClientAccount> accountList = (ObjectList<ClientAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (ClientAccount account: accountList.getList()){
            if (account.getUsername().equals(username)){
                accountList.remove(account);
                return true;
            }
        } return false;
    }

}
