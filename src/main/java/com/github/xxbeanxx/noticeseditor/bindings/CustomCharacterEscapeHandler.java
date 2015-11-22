package com.github.xxbeanxx.noticeseditor.bindings;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

/**
 * @author Greg Baker
 */
@SuppressWarnings("restriction")
public class CustomCharacterEscapeHandler implements CharacterEscapeHandler {

  public static final String JAXB_CHARACTER_ESCAPE_HANDLER = CharacterEscapeHandler.class.getName();

  @Override
  public void escape(char[] c, int start, int length, boolean isAttrVal, Writer writer)
      throws IOException {
    writer.write(c, start, length);
  }

}
