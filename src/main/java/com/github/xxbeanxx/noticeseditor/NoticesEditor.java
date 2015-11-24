package com.github.xxbeanxx.noticeseditor;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
public class NoticesEditor extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    final Parent root = FXMLLoader
        .load(getClass().getResource("/com/github/xxbeanxx/noticeseditor/ui/FXMLDocument.fxml"));
    final Scene scene = new Scene(root);
    final InputStream inputStream = getClass().getResourceAsStream("/application-xml.png");
    final Image image = new Image(inputStream);
    stage.getIcons().add(image);
    stage.setTitle("MSCA Notices Editor");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

}
