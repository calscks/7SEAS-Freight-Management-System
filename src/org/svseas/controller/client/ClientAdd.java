package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.utils.Validator;
import org.svseas.utils.manipulator.AccountManipulator;
import org.svseas.data.DataFile;
import org.svseas.model.account.ClientAccount;
import org.svseas.utils.Dialogue;

/**
 * Coded by Seong Chee Ken on 19/01/2017, 00:29.
 */
public class ClientAdd extends AccountManipulator {
    @FXML
    private StackPane clientadd_root;
    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXTextField username, companyName, registry_no, phone_no;
    @FXML
    private JFXPasswordField pwd, confirm_pwd;
    private String heading = "Password Not Match",
            body = "Password does not match with each other. Please re-type the password.",
            heading2 = "Username has been taken",
            body2 = "Either the account has already been exists, or the username has been taken.\n" +
                    "Please enter another unique username.";
    private ClientAccount client;

    @FXML
    public void initialize() {
        BooleanBinding binding = username.textProperty().isEmpty()
                .or(pwd.textProperty().isEmpty())
                .or(companyName.textProperty().isEmpty())
                .or(registry_no.textProperty().isEmpty())
                .or(phone_no.textProperty().isEmpty());
        btn_add.disableProperty().bind(binding);
        manipulate(btn_add);

        username.addEventFilter(KeyEvent.KEY_TYPED, Validator.validChar(20));
        companyName.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNoSpace(100));
        registry_no.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNo(20));
    }

    public <T> void initData(T type){} //nothing to init

    public void manipulate(JFXButton button) {
        button.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                client = new ClientAccount(username.getText(), pwd.getText(), companyName.getText(), registry_no.getText(), phone_no.getText());
                if (!pwd_match(pwd, confirm_pwd))
                    new Dialogue(heading, body, clientadd_root, Dialogue.DialogueType.ACCEPT);
                else if (!DataFile.analyse(DataFile.CLIENT)) {
                    create(client, clientadd_root, DataFile.CLIENT);
                } else if (acc_match(client, DataFile.CLIENT))
                    new Dialogue(heading2, body2, clientadd_root, Dialogue.DialogueType.ACCEPT);
                else {
                    create(client, clientadd_root, DataFile.CLIENT);
                }
            }
        });
    }

}
