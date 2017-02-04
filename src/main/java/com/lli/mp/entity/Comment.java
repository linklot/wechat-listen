package com.lli.mp.entity;

import org.springframework.data.annotation.Id;

public class Comment {
	@Id
	public String id;

	public String userId;
	public String userNickname;
	public String audioId;
	public String comment;
	public String time;
}
