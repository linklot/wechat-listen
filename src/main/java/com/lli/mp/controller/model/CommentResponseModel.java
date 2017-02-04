package com.lli.mp.controller.model;

import com.lli.mp.utils.DateTimeUtils;
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
		return DateTimeUtils.toCommentFriendlyString(time);
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
