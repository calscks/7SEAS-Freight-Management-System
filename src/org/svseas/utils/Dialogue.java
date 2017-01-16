package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Codes by Seong Chee Ken on 16/01/2017, 14:18. A dialogue model for usage across the system.
 */
public class Dialogue extends JFXDialog{

    private String heading, body;
    private StackPane root;

    public Dialogue(String heading, String body, StackPane root){
        this.heading = heading;
        this.body = body;
        this.root = root;
        set();
    }

    private void set(){
        this.setTransitionType(DialogTransition.CENTER);
        Label hlabel = new Label(heading);
        Text btext = new Text(body);
        AcceptButton ok = new AcceptButton();
        ok.setOnMouseClicked(event -> this.close());
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(hlabel);
        layout.setBody(btext);
        layout.setActions(ok);
        this.show(root);
    }
}

class AcceptButton extends JFXButton{
    AcceptButton(){
        setRipplerFill(Color.web("#f8f8f896"));
        setStyle("-fx-background-color: rgba(26, 188, 156,1.0);");
        setTextFill(Color.WHITE);
    }
}
