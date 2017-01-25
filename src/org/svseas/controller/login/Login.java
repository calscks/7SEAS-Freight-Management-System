package org.svseas.controller.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.svseas.data.DataFile;
import org.svseas.data.InputType;
import org.svseas.data.LoginData;
import org.svseas.model.account.AdminAccount;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.account.CustomerAccount;
import org.svseas.operations.AccountOperations;
import org.svseas.utils.Tester;
import org.svseas.utils.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Coded by Seong Chee Ken on 26/11/2016, 17:17.
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
    StackPane login_root;
    @FXML
    AnchorPane pane_Login;
    @FXML
    JFXButton acceptButton;


    public Login() {
    }

    @FXML
    public void initialize() {
        login_root.getChildren().remove(dialog);
        Font.loadFont(getClass().getResourceAsStream("/font/Aaargh.ttf"), 25);
        login_root.getStylesheets().add(Login.class.getResource("/resources/css/styles.css").toExternalForm());
        List<Labeled> list = new ArrayList<>();
        list.add(lblFreight);
        list.add(lblMS);

        tfName.addEventFilter(KeyEvent.KEY_TYPED, Validator.validCharNo(20));

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
                    AccountOperations<AdminAccount> admin = new AccountOperations<>(DataFile.ADMIN);
                    AccountOperations<CustomerAccount> customer = new AccountOperations<>(DataFile.CUSTOMER);
                    AccountOperations<ClientAccount> client = new AccountOperations<>(DataFile.CLIENT);

                    if (!admin.read(username, pwd) && !customer.read(username, pwd) && !client.read(username, pwd)) {
                        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
                        dialog.show(login_root);
                        acceptButton.setOnMouseClicked((e)-> dialog.close());
                    } else {
                        LoginData.username = username;
                        if (admin.read(username))
                            LoginData.authority = "Administrator";
                        else if (customer.read(username))
                            LoginData.authority = "Customer";
                        else if (client.read(username))
                            LoginData.authority = "Client";
                        Tester.SUCCESS.printer();
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/org/svseas/view/MainPane.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("7SEAS Freight Management System");
                            stage.show();
                            Stage prevStage = (Stage) pane_Login.getScene().getWindow();
                            prevStage.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
