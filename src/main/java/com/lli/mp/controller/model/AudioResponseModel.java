package com.lli.mp.controller.model;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class AudioResponseModel {
	public String id;
	public String title;
	public String description;
	public String publishDateTime;
	public String filename;

	public String getFriendlyDateTime() {
		DateTime dt = DateTime.parse(publishDateTime, ISODateTimeFormat.basicDateTime());
		return dt.toString("yyyy-MM-dd HH:mm");
	}
}
