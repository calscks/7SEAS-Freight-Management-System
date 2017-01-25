package org.svseas.controller.customer;

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
import org.svseas.data.LoginData;
import org.svseas.model.ObjectList;
import org.svseas.model.account.CustomerAccount;
import org.svseas.model.table.Customer;
import org.svseas.operations.AccountOperations;
import org.svseas.operations.XMLOperation;
import org.svseas.utils.ButtonStageShow;
import org.svseas.utils.Dialogue;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 17/01/2017, 21:04.
 */

public class CustManagement {
    @FXML
    private StackPane cusmgnt_root;
    @FXML
    private JFXTreeTableView<Customer> table_cust;
    @FXML
    private JFXButton btn_addCust, btn_refreshCust, btn_editCust, btn_delCust;
    @FXML
    private JFXTreeTableColumn<Customer, String> username, fullName, id_no;
    @FXML
    private JFXTextField field_custSearch;

    @FXML
    @SuppressWarnings({"unchecked", "Duplicates"})
    public void initialize() {

        if (Objects.equals(LoginData.authority, "Client")){
            btn_addCust.setVisible(false);
            btn_editCust.setVisible(false);
            btn_refreshCust.setVisible(false);
            btn_delCust.setVisible(false);
        }


        username.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> {
            if (username.validateValue(param))
                return param.getValue().getValue().usernameProperty();
            else
                return username.getComputedValue(param);
        });
        fullName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> {
            if (fullName.validateValue(param))
                return param.getValue().getValue().fullNameProperty();
            else
                return fullName.getComputedValue(param);
        });
        id_no.setCellValueFactory((TreeTableColumn.CellDataFeatures<Customer, String> param) -> {
            if (id_no.validateValue(param))
                return param.getValue().getValue().id_noProperty();
            else
                return id_no.getComputedValue(param);
        });

        populate();

        field_custSearch.textProperty().addListener((observable, oldValue, newValue) ->
                table_cust.setPredicate(customer -> customer.getValue().getUsername().contains(newValue) ||
                        customer.getValue().getFullName().contains(newValue) ||
                        customer.getValue().getId_no().contains(newValue)));

        ButtonStageShow adder = new ButtonStageShow(btn_addCust, "/org/svseas/view/CustAdd.fxml", "Add Customer");
        adder.operate();

        btn_editCust.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_cust.getSelectionModel().isEmpty()) {
                    new Dialogue("Customer Not Selected",
                            "Customer is not being selected. Please select a customer from the table.",
                            cusmgnt_root, Dialogue.DialogueType.ACCEPT);
                } else {
                    Customer customer = table_cust.getSelectionModel().getSelectedItem().getValue();
                    ObjectList<CustomerAccount> custList = (ObjectList<CustomerAccount>)
                            new XMLOperation(ObjectList.class, CustomerAccount.class).read(DataFile.CUSTOMER);
                    for (CustomerAccount accounts : custList.getList()) {
                        if (Objects.equals(customer.getUsername(), accounts.getUsername())) {
                            ButtonStageShow showEdit = new ButtonStageShow("/org/svseas/view/CustEdit.fxml",
                                    "Modify Customer");
                            showEdit.<CustomerAccount, CustEdit>operate(accounts);
                        }
                    }
                }
            }
        });

        btn_refreshCust.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) Platform.runLater(this::populate);
        });

        btn_delCust.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (table_cust.getSelectionModel().isEmpty()) {
                    new Dialogue("Customer Not Selected",
                            "Customer is not being selected. Please select a customer from the table.",
                            cusmgnt_root, Dialogue.DialogueType.ACCEPT);
                } else {
                    Customer customer = table_cust.getSelectionModel().getSelectedItem().getValue();
                    AccountOperations<CustomerAccount> accops = new AccountOperations<>(DataFile.CUSTOMER);
                    accops.delete(customer.getUsername());
                    Platform.runLater(this::populate);
                }
            }
        });

    }

    private void populate() {
        AccountOperations<CustomerAccount> ops = new AccountOperations<>(DataFile.CUSTOMER);
        ObjectList<CustomerAccount> custList = ops.read();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for (CustomerAccount cust : custList.getList()) {
            Customer customer = new Customer(cust.getUsername(), cust.getFullName(), cust.getId());
            customers.add(customer);
        }

        table_cust.setRoot(new RecursiveTreeItem<>(customers, RecursiveTreeObject::getChildren));
        table_cust.setShowRoot(false);
    }
}
