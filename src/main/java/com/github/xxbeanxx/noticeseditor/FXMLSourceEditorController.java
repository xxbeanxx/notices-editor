package com.github.xxbeanxx.noticeseditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
public class FXMLSourceEditorController {

	public enum ReturnValue { OK, CANCEL }
    
    private ReturnValue returnValue = ReturnValue.CANCEL;
    
    @FXML
    private Button cancelButton;

    @FXML
    private Button okButton;

    @FXML
    private TextArea textArea;

    @FXML
    void cancelButtonAction(ActionEvent event) {
        final Button button = (Button) event.getSource();
        final Scene scene = button.getScene();
        final Stage stage = (Stage) scene.getWindow();
        stage.close();
    }
    
    @FXML
    void okButtonAction(ActionEvent event) {
        final Button button = (Button) event.getSource();
        final Scene scene = button.getScene();
        final Stage stage = (Stage) scene.getWindow();
        this.returnValue = ReturnValue.OK;
        stage.close();
    }

    public String getText() {
        return textArea.getText();
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }

    public ReturnValue getReturnValue() {
        return returnValue;
    }

}
