package com.github.xxbeanxx.noticeseditor;

import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.xxbeanxx.noticeseditor.ui.SpringController;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
public class NoticesEditor extends Application {

  private ApplicationContext applicationContext;
  
  @Override
  public void init() throws Exception {
    applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
  }
  
  @Override
  public void start(Stage stage) throws Exception {
    final SpringController springController = applicationContext.getBean(SpringController.class);

    final InputStream inputStream = getClass().getResourceAsStream("/application-xml.png");
    final Image image = new Image(inputStream);
    stage.getIcons().add(image);

    stage.setTitle("MSCA Notices Editor");
    
    springController.init(stage);
    springController.load("/com/github/xxbeanxx/noticeseditor/ui/FXMLDocument.fxml");
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

}
