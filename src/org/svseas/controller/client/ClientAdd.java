package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.svseas.controller.ParentController;
import org.svseas.data.DataFile;
import org.svseas.model.account.ClientAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Dialogue;

import java.util.List;


/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:29.
 */
public class ClientAdd extends ParentController {
    @FXML
    private StackPane clientadd_root;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXTextField username, companyName, registry_no, phone_no;
    @FXML
    private JFXPasswordField pwd, confirm_pwd;
    private String heading = "Password Not Match",
            body = "Password does not match with each other. Please re-type the password.";
    private AccountOperations<ClientAccount> clientops;
    private ClientAccount client;
    private BooleanBinding binding;

    @FXML
    public void initialize() {
        binding = username.textProperty().isEmpty()
                .or(pwd.textProperty().isEmpty())
                .or(companyName.textProperty().isEmpty())
                .or(registry_no.textProperty().isEmpty())
                .or(phone_no.textProperty().isEmpty());
        btn_add.disableProperty().bind(binding);
    }

    public void add(JFXButton addButton) {
        addButton.setOnMouseClicked(e -> {
            if (!pwd_match(pwd, confirm_pwd)) new Dialogue(heading, body, clientadd_root, Dialogue.DialogueType.ACCEPT);
            else {
                client = new ClientAccount(username.getText(), pwd.getText(), companyName.getText(), registry_no.getText(), phone_no.getText());
                clientops = new AccountOperations<>(client, DataFile.CLIENT);
                clientops.create();
            }
        });
    }
}
