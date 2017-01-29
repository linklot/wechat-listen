package com.lli.mp.controller;

import com.lli.mp.controller.model.AudioResponseModel;
import com.lli.mp.service.AudioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private AudioService audioService;

	@Autowired
	public AdminController(AudioService audioService) {
		this.audioService = audioService;
	}

	@RequestMapping("")
	public String adminIndex(Model model) {
		model.addAttribute("audios", audioService.getAudiosForUI());
		return "adminAudios";
	}

	@RequestMapping("/createAudio")
	public String addAudio() {
		return "createAudio";
	}

	@RequestMapping(value = "/createAudio", method = RequestMethod.POST)
	public String createAudio(
			@RequestParam("title") String title,
			@RequestParam("audioFile") MultipartFile audioFile,
			@RequestParam("description") String description,
			@RequestParam("coverImg") MultipartFile coverImg) {

		if(isNotEmpty(title) && isNotEmpty(description) && audioFile.getSize() > 0 && coverImg.getSize() > 0) {
			try {
				audioService.saveAudio(title, description, coverImg, audioFile);
			} catch (IOException e) {
				LOGGER.error("Cannot upload audio. reason: {}", e.getMessage());
			}
		}
		return "adminAudios";
	}

	@RequestMapping("/audios/audio/{audioId}")
	public String editAudio(@PathVariable String audioId, Model model) {
		AudioResponseModel audioModel = audioService.getAudioForUI(audioId);
		model.addAttribute("audio", audioModel);
		LOGGER.info(audioModel.toString());
		return "adminUpdateAudio";
	}
}
