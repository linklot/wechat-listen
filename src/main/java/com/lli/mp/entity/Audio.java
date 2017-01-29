package com.lli.mp.entity;

import org.springframework.data.annotation.Id;

public class Audio {

	@Id
	public String id;

	public String title;
	public String coverImgName;
	public String description;
	public String publishDateTime;
	public int playTimes;
	public String fileName;
	public String length;

	@Override
	public String toString() {
		return "Audio{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", coverImgName='" + coverImgName + '\'' +
				", description='" + description + '\'' +
				", publishDateTime='" + publishDateTime + '\'' +
				", playTimes='" + playTimes + '\'' +
				", fileName='" + fileName + '\'' +
				", length='" + length + '\'' +
				'}';
	}
}
