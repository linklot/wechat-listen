package com.lli.mp.controller.model;

import com.lli.mp.entity.Audio;

import java.util.List;

public class AudioMgmtIndexModel {
	public String pageFlag;
	public List<Audio> audios;

	public AudioMgmtIndexModel(String pageFlag, List<Audio> audios) {
		this.pageFlag = pageFlag;
		this.audios = audios;
	}
}
