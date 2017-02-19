package com.lli.mp.controller.model;

import com.lli.mp.utils.DateTimeUtils;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AudioResponseModel {
	public String id;
	public String title;
	public String description;
	public String publishDateTime;
	public String filename;
	public int playTimes;
	public String friendlyDateTime;
	public int commentCount;

	public String getFriendlyDateTime() {
		if(isEmpty(friendlyDateTime)) {
			return DateTimeUtils.toFriendlyString(publishDateTime);
		}
		return friendlyDateTime;
	}
}
