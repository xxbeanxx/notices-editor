package com.github.xxbeanxx.noticeseditor.controls;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.XMLGregorianCalendar;

import org.jsoup.Jsoup;

import com.github.xxbeanxx.noticeseditor.bindings.Notices.Notice;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

/**
 * @author Greg Baker
 */
public class NoticeListCell extends ListCell<Notice> {

  private final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);

  @Override
  protected void updateItem(Notice notice, boolean empty) {
    super.updateItem(notice, empty);

    if (notice != null) {
      final XMLGregorianCalendar dateCreated = notice.getDateCreated();
      final GregorianCalendar gregorianCalendar = dateCreated.toGregorianCalendar();
      final Date time = gregorianCalendar.getTime();
      final String text = this.dateFormat.format(time);
      super.setText(text);

      final Tooltip tooltip = getTooltip(notice);
      super.setTooltip(tooltip);
    }
  }

  private Tooltip getTooltip(Notice notice) {
    final StringBuilder stringBuilder = new StringBuilder();

    final String englishTitle = notice.getTitle().getEnglish();
    final String englishText = notice.getText().getEnglish();

    final String frenchTitle = notice.getTitle().getFrench();
    final String frenchText = notice.getText().getFrench();

    // @formatter:off
    stringBuilder.append("English:").append(System.lineSeparator());
    stringBuilder.append("Title: ").append(Jsoup.parse(englishTitle).text()).append(System.lineSeparator());
    stringBuilder.append(Jsoup.parse(englishText).body().children()).append(System.lineSeparator());
    stringBuilder.append("--------------------------------").append(System.lineSeparator());
    stringBuilder.append("French:").append(System.lineSeparator());
    stringBuilder.append("Title: ").append(Jsoup.parse(frenchTitle).text()).append(System.lineSeparator());
    stringBuilder.append(Jsoup.parse(frenchText).body().children()).append(System.lineSeparator());
    // @formatter:on
    
    return new Tooltip(stringBuilder.toString());
  }

}
