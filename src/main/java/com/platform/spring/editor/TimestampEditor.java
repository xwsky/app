package com.platform.spring.editor;

import com.platform.core.utils.TextUtil;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.sql.Timestamp;





public class TimestampEditor extends PropertiesEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (TextUtil.isNotEmpty(text)) {
			if (text.indexOf(":") != -1) {
				setValue(Timestamp.valueOf(text));
			} else {
				setValue(Timestamp.valueOf(text + " 00:00:00"));
			}
		} else {
			super.setValue(null);
		}
	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}

}
