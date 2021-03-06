package com.github.xxbeanxx.noticeseditor.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
@Component
public class FXMLSourceEditorController extends AbstractFXMLController {

  public static final String FXML_LOCATION =
      "/com/github/xxbeanxx/noticeseditor/ui/FXMLSourceEditor.fxml";

  public enum ReturnValue {
    OK, CANCEL
  }

  private ReturnValue returnValue = ReturnValue.CANCEL;

  @FXML
  Button okButton;
  @FXML
  Button cancelButton;
  @FXML
  TextArea textArea;

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @FXML
  public void cancelButtonAction(ActionEvent event) {
    final Button button = (Button) event.getSource();
    final Scene scene = button.getScene();
    final Stage stage = (Stage) scene.getWindow();
    this.returnValue = ReturnValue.CANCEL;
    stage.close();
  }

  @FXML
  public void okButtonAction(ActionEvent event) {
    final Button button = (Button) event.getSource();
    final Scene scene = button.getScene();
    final Stage stage = (Stage) scene.getWindow();
    this.returnValue = ReturnValue.OK;
    stage.close();
  }

  public String getText() {
    return this.textArea.getText();
  }

  public void setText(String text) {
    this.textArea.setText(text);
  }

  public ReturnValue getReturnValue() {
    return this.returnValue;
  }

}
