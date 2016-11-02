package com.github.xxbeanxx.noticeseditor.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
@Component
public class SpringController implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  private final Map<String, AbstractFXMLController> controllers =
      Collections.synchronizedMap(new HashMap<String, AbstractFXMLController>());

  private Group root;

  private Stage stage;

  public void init(Stage stage) {
    this.root = new Group();
    this.stage = stage;
    this.stage.setScene(new Scene(this.root));
  }

  public void load(String fxml) {
    if (this.stage == null) {
      throw new IllegalStateException(
          "SpringController has not been initialized. Please call init(stage) first.");
    }

    final Class<?> controllerClass = getControllerClass(fxml);

    final AbstractFXMLController fxmlController =
        (AbstractFXMLController) this.applicationContext.getBean(controllerClass);

    if (this.controllers.get(fxmlController.getBeanName()) == null) {
      try {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        fxmlLoader.setControllerFactory(param -> fxmlController);
        final Parent parent = fxmlLoader.load();
        fxmlController.setParent(parent);
        this.controllers.put(fxmlController.getBeanName(), fxmlController);
        if (this.stage.isShowing() == false) {
          root.getChildren().add(0, parent);
          this.stage.show();
        }
      } catch (final IOException ioException) {
        throw new RuntimeException(ioException);
      }
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  protected Class<?> getControllerClass(String fxmlPath) {
    try {
      final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final URL location = getClass().getResource(fxmlPath);
      final Document document = builder.parse(location.openStream());
      final NamedNodeMap attributes = document.getDocumentElement().getAttributes();

      String fxControllerClassName = null;

      for (int i = 0; i < attributes.getLength(); i++) {
        final Node item = attributes.item(i);

        if (item.getNodeName()
            .equals(FXMLLoader.FX_NAMESPACE_PREFIX + ":" + FXMLLoader.CONTROLLER_KEYWORD)) {
          fxControllerClassName = item.getNodeValue();
          break;
        }
      }

      if (fxControllerClassName != null) {
        return ClassLoader.getSystemClassLoader().loadClass(fxControllerClassName);
      }
    } catch (ParserConfigurationException | SAXException | IOException
        | ClassNotFoundException ex) {
      // do nothing
    }

    return null;
  }

}
