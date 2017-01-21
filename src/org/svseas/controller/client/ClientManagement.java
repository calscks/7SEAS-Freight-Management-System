package org.svseas.controller.client;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.account.ClientAccount;
import org.svseas.model.table.Client;
import org.svseas.operations.AccountOperations;
import org.svseas.operations.XMLOperation;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 17/01/2017, 17:02.
 */
public class ClientManagement {
    @FXML
    private StackPane clientmgnt_root;
    @FXML
    private JFXButton btn_addClient, btn_refreshClient, btn_editClient, btn_delClient;
    @FXML
    private JFXTreeTableView<Client> table_client;
    @FXML
    private JFXTextField field_clientSearch;
    @FXML
    private JFXTreeTableColumn<Client, String> username, companyName, registry_no, phone_no;

    private DataFile df = DataFile.CLIENT;

    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        username.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client, String> param) -> {
            if (username.validateValue(param))
                return param.getValue().getValue().usernameProperty();
            else
                return username.getComputedValue(param);
        });
        companyName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client, String> param) -> {
            if (companyName.validateValue(param))
                return param.getValue().getValue().companyNameProperty();
            else
                return companyName.getComputedValue(param);
        });
        registry_no.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client, String> param) -> {
            if (registry_no.validateValue(param))
                return param.getValue().getValue().registry_noProperty();
            else
                return registry_no.getComputedValue(param);
        });
        phone_no.setCellValueFactory((TreeTableColumn.CellDataFeatures<Client, String> param) -> {
            if (phone_no.validateValue(param))
                return param.getValue().getValue().phone_noProperty();
            else
                return phone_no.getComputedValue(param);
        });

        populate();

        field_clientSearch.textProperty().addListener((observable, oldValue, newValue) ->
                table_client.setPredicate(client -> client.getValue().getUsername().contains(newValue) ||
                        client.getValue().getCompanyName().contains(newValue) ||
                        client.getValue().getRegistry_no().contains(newValue) ||
                        client.getValue().getPhone_no().contains(newValue)));

        ButtonStageShow adder = new ButtonStageShow(btn_addClient, "/org/svseas/view/ClientAdd.fxml", "Add Client");
        adder.operate();

        btn_editClient.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_client.getSelectionModel().isEmpty()) {
                    new Dialogue("Client Not Selected",
                            "Client is not being selected. Please select a client from the table.",
                            clientmgnt_root, Dialogue.DialogueType.ACCEPT);
                } else {
                    Client client = table_client.getSelectionModel().getSelectedItem().getValue();
                    ObjectList<ClientAccount> clientList = (ObjectList<ClientAccount>)
                            new XMLOperation(ObjectList.class, ClientAccount.class).read(df);
                    for (ClientAccount accounts : clientList.getList()) {
                        if (Objects.equals(client.getUsername(), accounts.getUsername())) {
                            ButtonStageShow showEdit = new ButtonStageShow("/org/svseas/view/ClientEdit.fxml",
                                    "Modify Client");
                            showEdit.<ClientAccount, ClientEdit>operate(accounts);
                        }
                    }
                }
            }
        });

        btn_refreshClient.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) Platform.runLater(this::populate);
        });

        btn_delClient.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_client.getSelectionModel().isEmpty()) {
                    new Dialogue("Client Not Selected",
                            "Client is not being selected. Please select a client from the table.",
                            clientmgnt_root, Dialogue.DialogueType.ACCEPT);
                } else {
                    Client client = table_client.getSelectionModel().getSelectedItem().getValue();
                    AccountOperations<ClientAccount> accops = new AccountOperations<>(df);
                    accops.delete(client.getUsername());
                    Platform.runLater(this::populate);
                }
            }
        });
    }

    private void populate() {
        AccountOperations<ClientAccount> ops = new AccountOperations<>(df);
        ObjectList<ClientAccount> clientList = ops.read();
        ObservableList<Client> clients = FXCollections.observableArrayList();
        for (ClientAccount cl : clientList.getList()) {
            Client client = new Client(cl.getUsername(), cl.getCompanyName(), cl.getRegistryNo(), cl.getPhoneNo());
            clients.add(client);
        }
        table_client.setRoot(new RecursiveTreeItem<>(clients, RecursiveTreeObject::getChildren));
        table_client.setShowRoot(false);
    }
}
