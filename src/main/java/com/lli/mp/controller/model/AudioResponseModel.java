package com.lli.mp.controller.model;

import com.lli.mp.utils.DateTimeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class AudioResponseModel {
	public String id;
	public String title;
	public String description;
	public String publishDateTime;
	public String filename;
	public int playTimes;

	public String getFriendlyDateTime() {
		return DateTimeUtils.toFriendlyString(publishDateTime);
	}
}
