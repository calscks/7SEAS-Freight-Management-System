package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.CustomerAccount;
import org.svseas.utils.Tester;

import javax.xml.bind.JAXBException;

/**
 * Codes by Seong Chee Ken on 14/01/2017, 18:05. CRUD operations on Account.
 */
public class CustomerAccOperations {

    private static XMLOperation xmlOperation = new XMLOperation(ObjectList.class, CustomerAccount.class);

    public CustomerAccOperations() throws JAXBException {
    }

    public static boolean create(CustomerAccount customerAccount){
        if (!DataFile.analyse(DataFile.CUSTOMER)){
            return false;
        }
        @SuppressWarnings("unchecked")
        ObjectList<CustomerAccount> accountList = (ObjectList<CustomerAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (CustomerAccount account: accountList.getList()){
            if (!account.equals(customerAccount)){
                accountList.add(customerAccount);
                xmlOperation.write(DataFile.CUSTOMER, accountList);
                return true;
            }
        } return false;
    }

    public static boolean read(String username){
        @SuppressWarnings("unchecked")
        ObjectList<CustomerAccount> accountList = (ObjectList<CustomerAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (CustomerAccount account: accountList.getList()){
            if (account.getUsername().equals(username)) {
                Tester.SUCCESS.printer();
                return true;
            }
        } Tester.FAIL.printer();
        return false;
    }

    public static boolean read(String username, String password){
        @SuppressWarnings("unchecked")
        ObjectList<CustomerAccount> accountList = (ObjectList<CustomerAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (CustomerAccount account: accountList.getList()){
            if (account.getUsername().equals(username) && account.getPassword().equals(password))
            {
                Tester.SUCCESS.printer();
                return true;
            }
        } Tester.FAIL.printer();
        return false;
    }

    public static boolean update(CustomerAccount CustomerAccount){
        @SuppressWarnings("unchecked")
        ObjectList<CustomerAccount> accountList = (ObjectList<CustomerAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (CustomerAccount account: accountList.getList()){
            if (account.equals(CustomerAccount)){
                accountList.remove(account);
                accountList.add(CustomerAccount);
                return true;
            }
        } return false;
    }

    public static boolean remove(String username){
        @SuppressWarnings("unchecked")
        ObjectList<CustomerAccount> accountList = (ObjectList<CustomerAccount>) xmlOperation.read(DataFile.CUSTOMER);
        for (CustomerAccount account: accountList.getList()){
            if (account.getUsername().equals(username)){
                accountList.remove(account);
                return true;
            }
        } return false;
    }
}
