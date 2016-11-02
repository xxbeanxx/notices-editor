package com.github.xxbeanxx.noticeseditor.ui;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;

import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * @author Greg Baker
 */
public abstract class AbstractFXMLController implements Initializable, BeanNameAware {

  @Autowired
  protected SpringController springController;
  
  protected String beanName;

  protected Parent parent;
  
  public Parent getParent() {
    return parent;
  }
  
  public void setParent(Parent parent) {
    this.parent = parent;
  }
  
  public String getBeanName() {
    return beanName;
  }
  
  @Override
  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }
  
}
