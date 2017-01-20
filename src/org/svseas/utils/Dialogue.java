package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Coded by Seong Chee Ken on 16/01/2017, 14:18. A custom dialog box for usage across the system.
 */
public class Dialogue extends JFXDialog{

    public enum DialogueType{
        ACCEPT,
        CONFIRMATION
    }

    private String heading, body;
    private StackPane root;
    private DialogueType dialogueType;
    private DialogueButton ok, cancel;

    public Dialogue(String heading, String body, StackPane root, DialogueType dialogueType){
        this.heading = heading;
        this.body = body;
        this.root = root;
        this.dialogueType = dialogueType;
        set();
    }

    private void set(){
        this.setTransitionType(DialogTransition.CENTER);
        Label hlabel = new Label(heading);
        Text btext = new Text(body);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(hlabel);
        layout.setBody(btext);
        switch (dialogueType){
            case ACCEPT:
                ok = new DialogueButton("OK");
                ok.setOnMouseClicked(event -> this.close());
                layout.setActions(ok);
                break;
            case CONFIRMATION:
                ok = new DialogueButton("Yes");
                cancel = new DialogueButton("No");
                cancel.setStyle("-fx-background-color: #D24D57;");
                layout.setActions(ok,cancel);
                break;
        }
        this.setContent(layout);
        this.show(root);
    }

    public DialogueButton getOk() {
        return ok;
    }

    public DialogueButton getCancel() {
        return cancel;
    }
}

class DialogueButton extends JFXButton{

    DialogueButton(String string){
        Color color = Color.web("f8f8f8", 0.15);
        setRipplerFill(color);
        setStyle("-fx-background-color: rgba(26, 188, 156,1.0);");
        setText(string);
        setTextFill(Color.WHITE);
    }

}
