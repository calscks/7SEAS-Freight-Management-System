package org.svseas.controller.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import org.svseas.data.InputType;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Tester;
import org.svseas.utils.Validator;

import java.util.ArrayList;
import java.util.List;


/**
 * Codes by Seong Chee Ken on 26/11/2016, 17:17.
 */
public class Login {
    @FXML
    Label lblFreight;
    @FXML
    Label lblMS;
    @FXML
    JFXButton btn_Login;
    @FXML
    JFXButton btn_custReg;
    @FXML
    JFXTextField tfName;
    @FXML
    JFXPasswordField tfPwd;
    @FXML
    JFXDialog dialog;
    @FXML
    StackPane pane_root;
    @FXML
    AnchorPane pane_Login;
    @FXML
    JFXButton acceptButton;


    public Login() {
    }

    @FXML
    public void initialize() {
        pane_root.getChildren().remove(dialog);
        Font.loadFont(getClass().getResourceAsStream("/font/Aaargh.ttf"), 25);
        List<Labeled> list = new ArrayList<>();
        list.add(lblFreight);
        list.add(lblMS);

        for (Labeled labeled : list) {
            labeled.setStyle("-fx-font-family: Aaargh Normal");
        }

        login_operation();
        field_operation();
    }

    private void login_operation() {

        btn_Login.setOnMouseClicked(event -> {
            String username = tfName.getText();
            String pwd = tfPwd.getText();
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Tester.TEST_BUTTON.printer();
                if (username.equals("") || pwd.equals("")) {
                    event.consume();
                } else {
                    if (!AccountOperations.read(username, pwd)) {
                        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                        dialog.show(pane_root);
                        acceptButton.setOnMouseClicked((e)-> dialog.close());
                    }
                }
            } else {
                event.consume();
            }
        });
    }

    private void field_operation() {
        new Validator(tfName, InputType.USER);
        new Validator(tfPwd);
    }
}
