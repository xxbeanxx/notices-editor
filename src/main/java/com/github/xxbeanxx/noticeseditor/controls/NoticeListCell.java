package com.github.xxbeanxx.noticeseditor.controls;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * @author Greg Baker
 */
public class NoticeListCell extends ListCell<Notice> {

  private static final String TOOLTIP_PATTERN = "<h1>{0}</h1><strong>{1}</strong><div>{2}</div>";

  private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void updateItem(Notice notice, boolean empty) {
    super.updateItem(notice, empty);

    if (notice != null) {
      final XMLGregorianCalendar dateCreated = notice.getDateCreated();
      final GregorianCalendar gregorianCalendar = dateCreated.toGregorianCalendar();
      final Date time = gregorianCalendar.getTime();
      final String text = this.dateFormat.format(time);
      super.setText(text);

      final String englishTitle = notice.getTitle().getEnglish();
      final String englishText = notice.getText().getEnglish();

      final String frenchTitle = notice.getTitle().getFrench();
      final String frenchText = notice.getText().getFrench();

      final WebView webView = new WebView();
      final WebEngine webEngine = webView.getEngine();


      webEngine.loadContent(
          MessageFormat.format(NoticeListCell.TOOLTIP_PATTERN, "English", englishTitle, englishText)
              + "<hr/>" + MessageFormat.format(NoticeListCell.TOOLTIP_PATTERN, "French",
                  frenchTitle, frenchText));

      final Tooltip tooltip = new Tooltip();
      tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      tooltip.setGraphic(webView);

      super.setTooltip(tooltip);
    }
  }

}
