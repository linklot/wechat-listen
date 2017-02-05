package com.lli.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

	private String audioDir;
	private String imgDir;

	@Autowired
	public FileService(@Value("${audioDir}") String audioDir,
	                   @Value("${imgDir}") String imgDir) {
		this.audioDir = audioDir;
		this.imgDir = imgDir;
	}

	public void saveAudioFile(MultipartFile file, String filename) throws IOException {
		Path filePath = Paths.get(audioDir).resolve(filename);
		saveFile(file, filePath);
	}

	public void saveImgFile(MultipartFile file, String filename) throws IOException {
		Path filePath = Paths.get(imgDir).resolve(filename);
		saveFile(file, filePath);
	}

	public void deleteAudioFile(String filename) throws IOException {
		Path filePath = Paths.get(audioDir).resolve(filename);
		Files.deleteIfExists(filePath);
	}

	public void deleteImgFile(String filename) throws IOException {
		Path filePath = Paths.get(imgDir).resolve(filename);
		Files.deleteIfExists(filePath);
	}

	public Path getCoverImgPath(String filename) {
		return Paths.get(imgDir).resolve(filename);
	}

	public Path getAudioPath(String filename) {
		return Paths.get(audioDir).resolve(filename);
	}

	private void saveFile(MultipartFile file, Path path) throws IOException {
		Files.deleteIfExists(path);
		Files.copy(file.getInputStream(), path);
	}
}
