package com.github.xxbeanxx.noticeseditor.controls;

import javax.xml.datatype.XMLGregorianCalendar;

import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice;
import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice.Text;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Greg Baker
 */
public class NoticeListCell extends ListCell<Notice> {

  @Override
  protected void updateItem(Notice notice, boolean empty) {
    super.updateItem(notice, empty);

    if (notice != null) {
      final XMLGregorianCalendar dateCreated = notice.getDateCreated();
      super.setText(dateCreated.toString());

      final Text text = notice.getText();
      final String englishText = text.getEnglish();

      final WebView webView = new WebView();
      final WebEngine webEngine = webView.getEngine();
      webEngine.loadContent(englishText);

      final Tooltip tooltip = new Tooltip();
      tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      tooltip.setGraphic(webView);

      super.setTooltip(tooltip);
    }
  }

}
