package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

/**
 * Coded by Seong Chee Ken on 17/01/2017, 17:02.
 */
public class ClientManagement {
    @FXML private StackPane clientmgnt_root;
    @FXML private JFXButton btn_addClient, btn_refreshClient, btn_editClient, btn_delClient;
    @FXML private JFXTreeTableView table_client;
}
