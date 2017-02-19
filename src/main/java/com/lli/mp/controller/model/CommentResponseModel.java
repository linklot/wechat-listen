package com.lli.mp.controller.model;

import com.lli.mp.utils.DateTimeUtils;

public class CommentResponseModel {
	public String id;
	public String userId;
	public String userNickname;
	public String audioId;
	public String comment;
	public String time;
	public String friendlyTime;
	public boolean hidden;

	public String getFriendlyTime() {
		if(null == friendlyTime) {
			return DateTimeUtils.toCommentFriendlyString(time);
		} else {
			return this.friendlyTime;
		}
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
