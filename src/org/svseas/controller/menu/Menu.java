package org.svseas.controller.menu;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.svseas.data.LoginData;
import org.svseas.utils.Dialogue;
import org.svseas.utils.Tester;

import java.io.IOException;
import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 27/11/2016, 18:14.
 */
public class Menu {
    @FXML
    private StackPane mainroot_root;
    @FXML
    private BorderPane main_root;
    @FXML
    private JFXButton btn_client, btn_ship, btn_port, btn_route, btn_customer, btn_routeship, btn_freight, btn_logout;
    @FXML
    private Label label_username, label_userType, label_route;
    @FXML
    private VBox vbox_route;

    @FXML
    public void initialize() throws IOException {
        label_username.setText(LoginData.username);
        label_userType.setText(LoginData.authority);
        if (Objects.equals(LoginData.authority, "Client")){
            vbox_route.setVisible(false);
            label_route.setVisible(false);
        } else if (Objects.equals(LoginData.authority, "Customer")){
            label_route.setVisible(false);
            vbox_route.setVisible(false);
        }

        StackPane clientMgntPane = FXMLLoader.load(getClass().getResource("/org/svseas/view/ClientManagement.fxml"));
        main_root.setCenter(clientMgntPane);
        main_root.getStylesheets().add(Menu.class.getResource("/css/styles.css").toExternalForm());
        new ButtonController(btn_client, "/org/svseas/view/ClientManagement.fxml", main_root);
        new ButtonController(btn_customer, "/org/svseas/view/CustManagement.fxml", main_root);
        new ButtonController(btn_ship, "/org/svseas/view/ShipManagement.fxml", main_root);
        new ButtonController(btn_port, "/org/svseas/view/PortManagement.fxml", main_root);
        new ButtonController(btn_route, "/org/svseas/view/RouteManagement.fxml", main_root);
        new ButtonController(btn_routeship, "/org/svseas/view/RouteShipManagement.fxml", main_root);
        new ButtonController(btn_freight, "/org/svseas/view/FreightManagement.fxml", main_root);

        btn_logout.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Dialogue dialogue = new Dialogue("Log Out",
                        "Are you sure you want to log out?",
                        mainroot_root, Dialogue.DialogueType.ACCEPT);
                dialogue.getOk().setOnMouseClicked(e1 -> {
                    Stage thisStage = (Stage) mainroot_root.getScene().getWindow();
                    thisStage.close();
                    Stage stage = new Stage();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/org/svseas/view/Login.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Login");
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}

class ButtonController {
    private JFXButton button;
    private BorderPane main_root;
    private String url;

    ButtonController(JFXButton button, String url, BorderPane main_root) {
        this.button = button;
        this.main_root = main_root;
        this.url = url;
        sidebtn_operations();
    }

    private void sidebtn_operations() {
        button.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                try {
                    StackPane loadPane = FXMLLoader.load(getClass().getResource(url));
                    main_root.getChildren().remove(main_root.getCenter());
                    main_root.setCenter(loadPane);
                    Tester.SUCCESS.printer();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}


