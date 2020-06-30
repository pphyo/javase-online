package com.jdc.app.util;

import java.text.DecimalFormat;

import javafx.util.StringConverter;

public class DecimalFormatedConverter extends StringConverter<Integer>{

	private static final DecimalFormat DF = new DecimalFormat("#,##0");
	
	@Override
	public Integer fromString(String str) {
		try {
			if(!str.isEmpty()) {
				return DF.parse(str).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString(Integer data) {
		
		if(null != data) {
			return DF.format(data);
		}
		
		return null;
	}

}
