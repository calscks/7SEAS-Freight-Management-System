package org.svseas.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import java.util.Objects;

public abstract class ParentController {
    protected boolean pwd_match(JFXPasswordField pass1, JFXPasswordField pass2){
        return Objects.equals(pass1.getText(), pass2.getText());
    }

    public abstract void add(JFXButton addButton);
}
