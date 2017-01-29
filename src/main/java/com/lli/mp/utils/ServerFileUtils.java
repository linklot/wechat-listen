package com.lli.mp.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class ServerFileUtils {

	public static String makeServerFilename(MultipartFile file) {
		String uuidFilename = UUID.randomUUID().toString();
		String originFilename = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(originFilename);
		return uuidFilename.concat(".").concat(ext);
	}
}
