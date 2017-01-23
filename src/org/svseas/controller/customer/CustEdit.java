package org.svseas.controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.utils.manipulator.AccountManipulator;
import org.svseas.data.DataFile;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:38.
 */
public class CustEdit extends AccountManipulator {
    @FXML
    private StackPane custedit_root;
    @FXML
    private JFXButton btn_edit, btn_cancel;
    @FXML
    private JFXTextField username, fullName, id_no;
    @FXML
    private JFXPasswordField pwd, confirm_pwd;
    private String heading = "Password Not Match",
            body = "Password does not match with each other. Please re-type the password.";
    private DataFile df = DataFile.CUSTOMER;

    @FXML
    public void initialize() {
        BooleanBinding binding = username.textProperty().isEmpty()
                .or(pwd.textProperty().isEmpty())
                .or(fullName.textProperty().isEmpty())
                .or(id_no.textProperty().isEmpty());
        btn_edit.disableProperty().bind(binding);
        username.setDisable(true);
        manipulate(btn_edit);
        btn_cancel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Stage thisStage = (Stage) custedit_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    public <T> void initData(T customer) {
        if (customer instanceof CustomerAccount) {
            username.setText(((CustomerAccount) customer).getUsername());
            pwd.setText(((CustomerAccount) customer).getPassword());
            fullName.setText(((CustomerAccount) customer).getFullName());
            id_no.setText(((CustomerAccount) customer).getId());
        }
    }

    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (!pwd_match(pwd, confirm_pwd))
                    new Dialogue(heading, body, custedit_root, Dialogue.DialogueType.ACCEPT);
                else {
                    CustomerAccount customer = new CustomerAccount(username.getText(), pwd.getText(),
                            fullName.getText(), id_no.getText());
                    AccountOperations<CustomerAccount> ops = new AccountOperations<>(customer, df);
                    ops.update(username.getText());
                }
            }
        });
    }
}
