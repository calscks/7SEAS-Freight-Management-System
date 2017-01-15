package org.svseas.data;

/**
 * Codes by Seong Chee Ken on 15/01/2017, 15:13.
 */
public enum InputType {
    USER("Username "),
    INPUT("Input ");

    private String string;

    InputType(String s){
        string = s;
    }

    public String getString() {
        return string;
    }
}
