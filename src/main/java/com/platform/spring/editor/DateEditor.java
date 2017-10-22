package com.platform.spring.editor;

import com.platform.core.utils.DateUtil;
import com.platform.core.utils.TextUtil;
import org.springframework.beans.propertyeditors.PropertiesEditor;


public class DateEditor extends PropertiesEditor {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (TextUtil.isNotEmpty(text)) {
			if (text.indexOf(":") != -1) {
				setValue(DateUtil.stringtoDate(text, DateUtil.yyyyMMddHHmmss));
			} else {
				setValue(DateUtil.stringtoDate(text, DateUtil.yyyyMMdd));
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
