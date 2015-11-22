package com.github.xxbeanxx.noticeseditor.bindings;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Greg Baker
 */
public class StringAdapter extends XmlAdapter<String, String> {

	@Override
	public String unmarshal(String v) throws Exception {
		if (v == null) return null;
		return v.trim();
	}

	@Override
	public String marshal(String v) throws Exception {
		if (v == null) return null;
		return "<![CDATA[" + v.trim() + "]]>";
	}

}