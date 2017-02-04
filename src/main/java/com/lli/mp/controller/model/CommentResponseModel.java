package com.lli.mp.controller.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

public class CommentResponseModel {
	public String id;
	public String userId;
	public String userNickname;
	public String audioId;
	public String comment;
	public String time;

	public String getFriendlyTime() {
		LocalDateTime pubTime = DateTime.parse(time, ISODateTimeFormat.dateTime()).toLocalDateTime();
		return pubTime.toString("yyyy-MM-dd HH:mm");
	}

	@Override
	public String toString() {
		return "CommentResponseModel{" +
				"id='" + id + '\'' +
				", userId='" + userId + '\'' +
				", userNickname='" + userNickname + '\'' +
				", audioId='" + audioId + '\'' +
				", comment='" + comment + '\'' +
				", time='" + time + '\'' +
				'}';
	}
}
