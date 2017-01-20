package org.svseas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.svseas.data.DataFile;
import org.svseas.model.account.Account;
import org.svseas.operations.AccountOperations;

import java.util.Objects;

//abstract class for controllers which need to manipulate accounts
public abstract class AccountManipulator{
    protected boolean pwd_match(JFXPasswordField pass1, JFXPasswordField pass2){
        return Objects.equals(pass1.getText(), pass2.getText());
    }
    
    protected boolean acc_match(Account account, DataFile dataFile){
        AccountOperations<Account> accops = new AccountOperations<>(account, dataFile);
        return accops.read(account.getUsername());
    }

    //manipulation means add or edit (update). Hence the button shall be add/edit button assigned to the controller.
    //delete is only available on the main management menus so it'll be not included here.
    public abstract void manipulate(JFXButton button);
}
