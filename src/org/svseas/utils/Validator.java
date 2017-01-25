package org.svseas.utils;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import org.svseas.data.InputType;

/**
 * Coded by Seong Chee Ken on 15/01/2017, 14:15. Copied from my previous assignment. Created by myself,
 * so it's technically not plagiarism, rather it's opportunistic.
 */
public class Validator {
    private RequiredFieldValidator validator;

    public Validator(JFXTextField JFXTextField, InputType inputType){
        validator = new RequiredFieldValidator();
        validator.setMessage(inputType.getString() + "Required!");
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"0.8em", ";", "error"));

        JFXTextField.getValidators().add(validator);
        JFXTextField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) JFXTextField.validate();
        });
    }

    public Validator(JFXPasswordField passwordField){
        validator = new RequiredFieldValidator();
        validator.setMessage("Password Required!");
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"0.8em", ";", "error"));
        passwordField.getValidators().add(validator);
        passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) passwordField.validate();
        });
    }

    public static EventHandler<KeyEvent> validNo(final Integer maxLength) {
        return e -> {
            JFXTextField tf = (JFXTextField) e.getSource();
            if (tf.getText().length() >= maxLength) {
                e.consume();
            }
            if(e.getCharacter().matches("[0-9.]")){
                if(tf.getText().contains(".") && e.getCharacter().matches("[.]")){
                    e.consume();
                }else if(tf.getText().length() == 0 && e.getCharacter().matches("[.]")){
                    e.consume();
                }
            }else{
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validChar(final Integer maxLength) {
        return e -> {
            JFXTextField txt_JFXTextField = (JFXTextField) e.getSource();
            if (txt_JFXTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNo(final Integer maxLength) {
        return e -> {
            JFXTextField txt_JFXTextField = (JFXTextField) e.getSource();
            if (txt_JFXTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNoCommaDot(final Integer maxLength) {
        return e -> {
            JFXTextField txt_JFXTextField = (JFXTextField) e.getSource();
            if (txt_JFXTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9,. ]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validPrice(final Integer maxLength) {
        return e -> {
            JFXTextField txt_JFXTextField = (JFXTextField) e.getSource();
            if (txt_JFXTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[0-9.]")) {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> validCharNoSpace(final Integer maxLength) {
        return e -> {
            JFXTextField txt_JFXTextField = (JFXTextField) e.getSource();
            if (txt_JFXTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z0-9 ]")) {
                e.consume();
            }
        };
    }
}
