package com.lli.mp.controller.model;

import java.util.List;

public class PaginatedAudioResponseModel {
	public boolean isLast;
	public int pageNumber;
	public int pageSize;
	public List<AudioResponseModel> audioModels;
}
