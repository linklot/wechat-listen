package com.lli.mp.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShowHideCommentsRequestModel {
	public String audioId;
	public List<String> idsToHide;
}
