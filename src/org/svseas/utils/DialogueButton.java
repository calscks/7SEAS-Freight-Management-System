package org.svseas.utils;

import com.jfoenix.controls.JFXButton;
import javafx.scene.paint.Color;

public class DialogueButton extends JFXButton {

    DialogueButton(String string){
        Color color = Color.web("f8f8f8", 0.15);
        setRipplerFill(color);
        setStyle("-fx-background-color: rgba(26, 188, 156,1.0);");
        setText(string);
        setTextFill(Color.WHITE);
    }

}
