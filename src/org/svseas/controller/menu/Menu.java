package org.svseas.controller.menu;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.svseas.utils.Tester;

import java.io.IOException;

/**
 * Coded by Seong Chee Ken on 27/11/2016, 18:14.
 */
public class Menu {
    @FXML
    private BorderPane main_root;
    @FXML
    private JFXButton btn_client, btn_clientShip, btn_port, btn_route, btn_customer;
    private StackPane clientMgntPane;

    @FXML
    public void initialize() throws IOException {
        clientMgntPane = FXMLLoader.load(getClass().getResource("/org/svseas/view/ClientManagement.fxml"));
        main_root.setCenter(clientMgntPane);
        main_root.getStylesheets().add(Menu.class.getResource("/css/styles.css").toExternalForm());
        new ButtonController(btn_client, "/org/svseas/view/ClientManagement.fxml", main_root);
        new ButtonController(btn_customer, "/org/svseas/view/CustManagement.fxml", main_root);
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


