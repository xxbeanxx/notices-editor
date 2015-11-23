package com.github.xxbeanxx.noticeseditor.bindings;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * @author Greg Baker
 */
public class CustomCharacterEscapeHandler implements CharacterEscapeHandler {

  @Override
  public void escape(char[] c, int start, int length, boolean isAttrVal, Writer writer)
      throws IOException {
    writer.write(c, start, length);
  }

}
