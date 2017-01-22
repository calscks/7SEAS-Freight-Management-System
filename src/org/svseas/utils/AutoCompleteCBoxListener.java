package org.svseas.utils;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.skins.JFXComboBoxListViewSkin;
import javafx.collections.ObservableList;
import javafx.scene.control.IndexRange;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Coded by Seong Chee Ken on 22/01/2017, 17:41.
 */
public class AutoCompleteCBoxListener<T> {
    private JFXComboBox<T> comboBox;
    private StringBuilder sb;
    private int lastLength;

    /**
     * Credits to JulianG and Mateus Viccari from Stack Overflow!<br>
     * Following codes are re-furbished and re-design by both experts. They answered the related
     * auto-complete questions below:<br>
     *
     * See:
     * <a href="http://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx">
     * The question and answers from Stack Overflow</a><br>
     *
     * Re-designed based on:
     * <a href="http://tech.chitgoks.com/2013/08/20/how-to-create-autocomplete-combobox-or-textfield-in-java-fx-2/">
     * This original structure</a>
     *
     * How to call this function:
     * new AutoCompleteCBoxListener(JFXCombobox cboxName) <br>
     * Code was modified for my previous software project assignment in diploma, now re-modified for this assignment
     * with JFoenix integration. Strictly for JFXComboBox usage only.
     */
    public AutoCompleteCBoxListener(JFXComboBox<T> comboBox){
        this.comboBox = comboBox;
        sb = new StringBuilder();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(event -> {
            // this variable is used to bypass the auto complete process if the length is the same.
            // this occurs if user types fast, the length of textfield will record after the user
            // has typed after a certain delay.
            if (lastLength != (comboBox.getJFXEditor().getLength() - comboBox.getJFXEditor().getSelectedText().length()))
                lastLength = comboBox.getJFXEditor().getLength() - comboBox.getJFXEditor().getSelectedText().length();

            if (event.isControlDown() || event.getCode() == KeyCode.BACK_SPACE ||
                    event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT ||
                    event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.HOME ||
                    event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB
                    )
                return;

            if (event.getCode().equals(KeyCode.DOWN)) {
                comboBox.show();
                return;
            }

            IndexRange ir = comboBox.getJFXEditor().getSelection();
            sb.delete(0, sb.length());
            sb.append(comboBox.getJFXEditor().getText());
            // remove selected string index until end so only unselected text will be recorded
            try {
                sb.delete(ir.getStart(), sb.length());
            } catch (Exception ignored) { }

            ObservableList<T> items = comboBox.getItems();
            for (int i=0; i<items.size(); i++) {
                if (items.get(i) != null && comboBox.getJFXEditor().getText() != null && items.get(i).toString().toLowerCase().startsWith(comboBox.getJFXEditor().getText().toLowerCase())) {
                    try {
                        comboBox.getJFXEditor().setText(sb.toString() + items.get(i).toString().substring(sb.toString().length()));
                        comboBox.setValue(items.get(i));
                        comboBox.getSelectionModel().select(i);
                    } catch (Exception e) {
                        comboBox.getJFXEditor().setText(sb.toString());
                    }
                    comboBox.getJFXEditor().positionCaret(sb.toString().length());
                    comboBox.getJFXEditor().selectEnd();
                    break;
                }
            }
        });

        this.comboBox.getJFXEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                lastLength = 0;
                sb.delete(0, sb.length());
                selectClosestResultBasedOnTextFieldValue(false, false);
            }
        });

        this.comboBox.setOnMouseClicked(event -> selectClosestResultBasedOnTextFieldValue(true, true));
    }

    /*
     * selectClosestResultBasedOnTextFieldValue() - selects the item and scrolls to it when
     * the popup is shown.
     *
     * parameters:
     *  affect - true if combobox is clicked to show popup so text and caret position will be readjusted.
     *  inFocus - true if combobox has focus. If not, programmatically press enter key to add new entry to list.
     *
     */
    private void selectClosestResultBasedOnTextFieldValue(boolean affect, boolean inFocus) {
        ObservableList items = AutoCompleteCBoxListener.this.comboBox.getItems();
        boolean found = false;
        for (int i=0; i<items.size(); i++) {
            if (items.get(i) != null && AutoCompleteCBoxListener.this.comboBox.getJFXEditor().getText() != null && AutoCompleteCBoxListener.this.comboBox.getJFXEditor().getText().toLowerCase().equals(items.get(i).toString().toLowerCase())) {
                try {
                    ListView lv = ((JFXComboBoxListViewSkin) AutoCompleteCBoxListener.this.comboBox.getSkin()).getListView();
                    lv.getSelectionModel().clearAndSelect(i);
                    lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
                    found = true;
                    break;
                } catch (Exception ignored) { }
            }
        }

        String s = comboBox.getJFXEditor().getText();
        System.out.println("Found? " + found);
        if (!found && affect) {
            comboBox.getSelectionModel().clearSelection();
            comboBox.getJFXEditor().setText(s);
            comboBox.getJFXEditor().end();
        }

        if (!found) {
            comboBox.getJFXEditor().setText(null);
            comboBox.getSelectionModel().select(null);
            comboBox.setValue(null);
        }

        if (!inFocus && comboBox.getJFXEditor().getText() != null && comboBox.getJFXEditor().getText().trim().length() > 0) {
            // press enter key programmatically to have this entry added
//            KeyEvent ke = new KeyEvent(comboBox, KeyCode.ENTER.toString(), KeyCode.ENTER.getName(), KeyCode.ENTER.impl_getCode(), false, false, false, false, KeyEvent.KEY_RELEASED);
            KeyEvent ke = new KeyEvent(KeyEvent.KEY_RELEASED, KeyCode.ENTER.toString(), KeyCode.ENTER.toString(), KeyCode.ENTER, false, false, false, false);
            comboBox.fireEvent(ke);
        }
    }
}
