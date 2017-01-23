package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.svseas.controller.client.ShipPreview;
import org.svseas.model.table.Preview;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 16:34. IntelliJ is automatically highlighting duplicated codes as short
 * as less than 10 lines. No other choice to create another class just for this. <br>
 *     USE THIS FOR ALL PREVIEW BUTTONS
 */
public class Previewer {
    private JFXButton button;
    private Label total;
    private ObservableList<Preview> list;
    private StackPane root;

    public Previewer(JFXButton button, Label total, ObservableList<Preview> list, StackPane root) {
        this.button = button;
        this.total = total;
        this.list = list;
        this.root = root;
        preview();
    }

    private void preview(){
        button.setOnMouseClicked(event -> {
            if (Objects.equals(total.getText(), "0.00")){
                new Dialogue("Can't generate list", "Nothing to preview.", root, Dialogue.DialogueType.ACCEPT);
            } else {
                ButtonStageShow show = new ButtonStageShow("/org/svseas/view/Preview.fxml", "Preview");
                show.<ObservableList<Preview>, ShipPreview>operate(list);
            }
        });
    }
}
