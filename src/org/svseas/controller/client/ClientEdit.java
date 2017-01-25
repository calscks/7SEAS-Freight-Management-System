package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.svseas.utils.Validator;
import org.svseas.utils.manipulator.AccountManipulator;
import org.svseas.data.DataFile;
import org.svseas.model.account.ClientAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:38.
 */
public class ClientEdit extends AccountManipulator {
    @FXML
    private StackPane clientedit_root;
    @FXML
    private JFXButton btn_edit, btn_cancel;
    @FXML
    private JFXTextField username, companyName, registry_no, phone_no;
    @FXML
    private JFXPasswordField pwd, confirm_pwd;

    private DataFile df = DataFile.CLIENT;

    private String heading = "Password Not Match",
            body = "Password does not match with each other. Please re-type the password.";

    @FXML
    public void initialize(){

        username.addEventFilter(KeyEvent.KEY_TYPED, Validator.validChar(20));
        companyName.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNoSpace(100));
        registry_no.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNo(20));

        username.setDisable(true);
        BooleanBinding binding = username.textProperty().isEmpty()
                .or(pwd.textProperty().isEmpty())
                .or(companyName.textProperty().isEmpty())
                .or(registry_no.textProperty().isEmpty())
                .or(phone_no.textProperty().isEmpty());
        btn_edit.disableProperty().bind(binding);
        manipulate(btn_edit);
        btn_cancel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                Stage thisStage = (Stage) clientedit_root.getScene().getWindow();
                thisStage.close();
            }
        });
    }

    @Override
    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(e->{
            if (e.getButton().equals(MouseButton.PRIMARY)){
                if (!pwd_match(pwd, confirm_pwd))
                    new Dialogue(heading, body, clientedit_root, Dialogue.DialogueType.ACCEPT);
                else {
                    ClientAccount client = new ClientAccount(username.getText(), pwd.getText(), companyName.getText(),
                            registry_no.getText(), phone_no.getText());
                    AccountOperations<ClientAccount> ops = new AccountOperations<>(client, df);
                    ops.update(username.getText());
                }
            }
        });
    }

    @Override
    public <T> void initData(T client) {
        if (client instanceof ClientAccount){
            username.setText(((ClientAccount) client).getUsername());
            pwd.setText(((ClientAccount) client).getPassword());
            companyName.setText(((ClientAccount) client).getCompanyName());
            registry_no.setText(((ClientAccount) client).getRegistryNo());
            phone_no.setText(((ClientAccount) client).getPhoneNo());
        }
    }
}
