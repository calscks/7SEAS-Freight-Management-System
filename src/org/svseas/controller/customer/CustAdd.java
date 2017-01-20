package org.svseas.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.controller.AccountManipulator;
import org.svseas.data.DataFile;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:34.
 */
public class CustAdd extends AccountManipulator {
    @FXML
    private StackPane custadd_root;
    @FXML
    private JFXButton btn_add;
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
        btn_add.disableProperty().bind(binding);
        manipulate(btn_add);
    }

    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                customer = new CustomerAccount(username.getText(), pwd.getText(), fullName.getText(), id_no.getText());
                if (!pwd_match(pwd, confirm_pwd))
                    new Dialogue(heading, body, custadd_root, Dialogue.DialogueType.ACCEPT);
                else if (!DataFile.analyse(df)) {
                    create(customer);
                } else if (acc_match(customer, df))
                    new Dialogue(heading2, body2, custadd_root, Dialogue.DialogueType.ACCEPT);
                else {
                    create(customer);
                }
            }
        });
    }

    private void create(CustomerAccount customer){
        custops = new AccountOperations<>(customer, df);
        custops.create();
        Stage thisStage = (Stage) custadd_root.getScene().getWindow();
        thisStage.close();
    }
}
