package org.svseas.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.svseas.controller.AccountManipulator;
import org.svseas.data.DataFile;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;

/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:38.
 */
public class CustEdit extends AccountManipulator {
    @FXML
    private StackPane custedit_root;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXTextField username, fullName, id_no;
    @FXML
    private JFXPasswordField pwd, confirm_pwd;
    private String heading = "Password Not Match",
            body = "Password does not match with each other. Please re-type the password.",
            heading2 = "Username has been taken",
            body2 = "Either the account has already been exists, or the username has been taken.\n" +
                    "Please enter another unique username.";
    private AccountOperations<CustomerAccount> custops;
    private CustomerAccount customer;
    private DataFile df = DataFile.CUSTOMER;

    @FXML
    public void initialize() {
        BooleanBinding binding = username.textProperty().isEmpty()
                .or(pwd.textProperty().isEmpty())
                .or(fullName.textProperty().isEmpty())
                .or(id_no.textProperty().isEmpty());
        btn_edit.disableProperty().bind(binding);
    }

    public void manipulate(JFXButton button) {

    }
}
