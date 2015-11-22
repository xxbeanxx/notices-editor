package com.github.xxbeanxx.noticeseditor.bindings;

/**
 * @author Greg Baker
 */
public class NoticesHeader {

  public static final String HEADER =
      "<?xml version='1.0' encoding='ISO-8859-1' standalone='yes'?>\r\n" + "<!DOCTYPE notices [\r\n"
          + "	<!ELEMENT text (english, french)>\r\n" + "	<!ELEMENT title (english, french)>\r\n"
          + "	<!ELEMENT french (#PCDATA)>\r\n"
          + "	<!ELEMENT notice (title?, text, display-date, effective-date, expiry-date, date-created)>\r\n"
          + "	<!ELEMENT english (#PCDATA)>\r\n" + "	<!ELEMENT notices (notice+)>\r\n"
          + "	<!ELEMENT expiry-date (#PCDATA)>\r\n" + "	<!ELEMENT date-created (#PCDATA)>\r\n"
          + "	<!ELEMENT display-date (#PCDATA)>\r\n" + "	<!ELEMENT effective-date (#PCDATA)>\r\n"
          + "]>\r\n" + "\r\n" + "<!-- NOTE: date formats are yyyy-mm-dd -->\r\n" + "\r\n";

}
