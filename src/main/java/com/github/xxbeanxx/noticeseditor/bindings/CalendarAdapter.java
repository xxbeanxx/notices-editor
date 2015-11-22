package com.github.xxbeanxx.noticeseditor.bindings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Greg Baker
 */
public class CalendarAdapter extends XmlAdapter<String, XMLGregorianCalendar> {

  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public String marshal(XMLGregorianCalendar v) throws Exception {
    return dateFormat.format(v.toGregorianCalendar().getTime());
  }

  @Override
  public XMLGregorianCalendar unmarshal(String v) throws Exception {
    final GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(dateFormat.parse(v));
    return  DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar.get(Calendar.YEAR), gregorianCalendar.get(Calendar.MONTH)+1, gregorianCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0, 0);
  }

}
