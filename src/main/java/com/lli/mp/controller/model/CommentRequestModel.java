package com.lli.mp.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequestModel {
	public String audioId;
	public String comment;

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"audioId\":\""+ audioId +"\",");
		sb.append("\"comment\":\""+ comment +"\"");
		sb.append("}");
		return sb.toString();
	}
}
