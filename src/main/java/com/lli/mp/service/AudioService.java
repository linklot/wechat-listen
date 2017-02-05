package com.lli.mp.service;

import com.lli.mp.controller.model.AudioResponseModel;
import com.lli.mp.entity.Audio;
import com.lli.mp.repository.AudioRepository;
import com.lli.mp.utils.ServerFileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class AudioService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private AudioRepository audioRepository;
	private FileService fileService;

	@Autowired
	public AudioService(AudioRepository audioRepository, FileService fileService) {
		this.audioRepository = audioRepository;
		this.fileService = fileService;
	}

	@Transactional
	public void saveAudio(String title, String description,
	                      MultipartFile imgFile, MultipartFile audioFile) throws IOException {
		String serverAudioFilename = ServerFileUtils.makeServerFilename(audioFile);
		fileService.saveAudioFile(audioFile, serverAudioFilename);

		String serverImgFilename = ServerFileUtils.makeServerFilename(imgFile);
		fileService.saveImgFile(imgFile, serverImgFilename);

		Audio audio = makeAudioEntity(title, description, serverImgFilename, serverAudioFilename, audioFile);
		audioRepository.save(audio);
	}

	@Transactional
	public void deleteAudios(List<String> audioIds) {
		audioIds.forEach(id -> {
			Audio audio = audioRepository.findOne(id);
			String audioFilename = audio.fileName;
			String imgFilename = audio.coverImgName;

			try {
				fileService.deleteAudioFile(audioFilename);
				fileService.deleteImgFile(imgFilename);
			} catch(IOException e) {
				e.printStackTrace();
				LOGGER.error("Cannot delete audio: {}", id);
			}

			audioRepository.delete(audio);
		});
	}

	public List<AudioResponseModel> getAudiosForUI() {
		return audioRepository.findAll(orderByPublishDateTime()).stream()
				.map(entity -> {
					AudioResponseModel uiModel = new AudioResponseModel();
					uiModel.id = entity.id;
					uiModel.title = entity.title;
					uiModel.description = entity.description;
					uiModel.publishDateTime = entity.publishDateTime;
					uiModel.filename = entity.fileName;
					uiModel.playTimes = entity.playTimes;
					return uiModel;
				}).collect(Collectors.toList());
	}

	public Path getCoverImgPath(String audioId) {
		Audio audio = audioRepository.findOne(audioId);
		String imgFilename = audio.coverImgName;
		return fileService.getCoverImgPath(imgFilename);
	}

	public Path getAudioPath(String audioId) {
		Audio audio = audioRepository.findOne(audioId);
		String audioFilename = audio.fileName;
		return fileService.getAudioPath(audioFilename);
	}

	public AudioResponseModel getAudioForUI(String audioId) {
		Audio audio = audioRepository.findOne(audioId);
		AudioResponseModel model = new AudioResponseModel();
		model.id = audio.id;
		model.title = audio.title;
		model.description = audio.description;
		model.filename = audio.fileName;
		model.playTimes = audio.playTimes;
		model.publishDateTime = audio.publishDateTime;
		return model;
	}

	@Transactional
	public void increaseAudioPlayTimes(String audioId) {
		Audio audio = audioRepository.findOne(audioId);
		audio.playTimes = audio.playTimes + 1;
		audioRepository.save(audio);
	}

	@Transactional
	public void updateAudio(String audioId, String title, String description, MultipartFile audioFile, MultipartFile coverImg) throws IOException {
		Audio audio = audioRepository.findOne(audioId);

		if(isNotEmpty(title)) {
			audio.title = title.trim();
		}

		if(isNotEmpty(description)) {
			audio.description = description.trim();
		}

		if(audioFile.getSize() > 0) {
			fileService.saveAudioFile(audioFile, audio.fileName);
		}

		if(coverImg.getSize() > 0) {
			fileService.deleteImgFile(audio.coverImgName);
			String serverImgFilename = ServerFileUtils.makeServerFilename(coverImg);
			fileService.saveImgFile(coverImg, serverImgFilename);
			audio.coverImgName = serverImgFilename;
		}

		audioRepository.save(audio);
	}

	private Audio makeAudioEntity(String title, String description, String serverImgFilename,
	                              String serverAudioFilename, MultipartFile audioFile) {
		Audio audio = new Audio();

		audio.title = title;
		audio.coverImgName = serverImgFilename;
		audio.description = description;
		audio.publishDateTime = DateTime.now().toString(ISODateTimeFormat.basicDateTime());
		audio.playTimes = 0;
		audio.fileName = serverAudioFilename;
		audio.length = "";

		return audio;
	}

	private Sort orderByPublishDateTime() {
		return new Sort(Sort.Direction.DESC, "publishDateTime");
	}
}
