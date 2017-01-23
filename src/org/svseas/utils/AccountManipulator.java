package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.svseas.data.DataFile;
import org.svseas.model.account.Account;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken. Abstract class for controllers which need to manipulate accounts
 */
public abstract class AccountManipulator extends Manipulator{
    protected boolean pwd_match(JFXPasswordField pass1, JFXPasswordField pass2){
        return Objects.equals(pass1.getText(), pass2.getText());
    }
    
    protected boolean acc_match(Account account, DataFile dataFile){
        AccountOperations<Account> accops = new AccountOperations<>(account, dataFile);
        return accops.read(account.getUsername());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Account> void create (T account, Pane pane, DataFile dataFile){
        AccountOperations<T> ops;
        if (account instanceof CustomerAccount) {
            ops = new AccountOperations(account, dataFile);
            ops.create();
        } else if (account instanceof ClientAccount) {
            ops = new AccountOperations<>(account, dataFile);
            ops.create();
        }
        Stage thisStage = (Stage) pane.getScene().getWindow();
        thisStage.close();
    }

    //manipulation means add or edit (update). Hence the button shall be add/edit button assigned to the controller.
    //delete is only available on the main management menus so it'll be not included here.
    public abstract void manipulate(JFXButton button);

    //initData must be used for params passing such as during editing (passing params to different stage)
    public abstract <T> void initData(T type);
}
