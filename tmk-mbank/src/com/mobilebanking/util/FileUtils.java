package com.mobilebanking.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static String getBasePath() {
		String basePath = "";
		if (SystemUtils.IS_OS_WINDOWS) {
			basePath = StringConstants.OS_WINDOWS_DIRECTORY;
		} else if (SystemUtils.IS_OS_LINUX) {
			basePath = StringConstants.OS_LINUX_DIRECTORY;
		} else if (SystemUtils.IS_OS_MAC) {
			basePath = StringConstants.OS_LINUX_DIRECTORY;
		}
		return basePath;
	}

	public static String getSamplePath() {
		if (SystemUtils.IS_OS_WINDOWS) {
			return StringConstants.OS_WINDOWS_DIRECTORY_SAMPLE;
		} else if (SystemUtils.IS_OS_LINUX) {
			return StringConstants.OS_LINUX_DIRECTORY_SAMPLE;
		} 
		return "";
	}

	public static String getFilePath(String fileName, String type) {
		String filePath = "";
		String basePath = "";
		if (SystemUtils.IS_OS_WINDOWS) {
			basePath = StringConstants.OS_WINDOWS_DIRECTORY;
		} else if (SystemUtils.IS_OS_LINUX) {
			basePath = StringConstants.OS_LINUX_DIRECTORY;
		} else if (SystemUtils.IS_OS_MAC) {
			basePath = StringConstants.OS_LINUX_DIRECTORY;
		}
		filePath = getPath(basePath, fileName, type);
		return filePath;
	}

	private static String getPath(String basePath, String fileName, String type) {
		String separator = File.separator;
		String filePath = basePath + separator + type;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		filePath += separator + fileName;
		return filePath;
	}

	public static void upload(MultipartFile sourceFile, String destination) {
		File destinationFile = new File(destination);
		try {
			sourceFile.transferTo(destinationFile);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	public static boolean isValidFileType(String filename, String... fileExtension) {
		boolean valid = false;
		for (String ext : fileExtension) {
			if (ext.equalsIgnoreCase(FilenameUtils.getExtension(filename))) {
				valid = true;
			}
		}
		return valid;
	}
}