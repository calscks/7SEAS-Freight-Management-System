package org.svseas.utils;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import org.svseas.data.InputType;

/**
 * Codes by Seong Chee Ken on 15/01/2017, 14:15.
 */
public class Validator {
    private RequiredFieldValidator validator;

    public Validator(JFXTextField textField, InputType inputType){
        validator = new RequiredFieldValidator();
        validator.setMessage(inputType.getString() + "Required!");
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"0.8em", ";", "error"));

        textField.getValidators().add(validator);
        textField.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) textField.validate();
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
}
