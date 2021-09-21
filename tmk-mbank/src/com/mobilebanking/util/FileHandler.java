package com.mobilebanking.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mobilebanking.entity.Document;

@Component
public class FileHandler {

	public String multipartSingleNameResolver(MultipartFile file) {

		if (file != null) {
			if (!file.getOriginalFilename().trim().equals("")) {
				return file.getOriginalFilename();
			} else {
				return "";
			}
		} else {
			return "";
		}

	}

	public void homeScreenImageHandler(MultipartFile file, Document document, String swiftCode) {
		if (!file.isEmpty()) {
			try {
				File dir;
				byte[] bytes = file.getBytes();
				String filePath = "";
				if (SystemUtils.IS_OS_LINUX) {
					filePath = "/opt/mbank/homescreenimage/" + swiftCode;
				} else {
					filePath = "D:/mbank/homescreenimage/" + swiftCode;
				}
				dir = new File(filePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				dir = new File(filePath + File.separator + document.getIdentifier());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
						filePath + "/" + document.getIdentifier() + "." + document.getExtention()));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void documentHandler(MultipartFile file, Document document) {
		if (!file.isEmpty()) {
			try {
				File dir;
				byte[] bytes = file.getBytes();
				if (SystemUtils.IS_OS_LINUX) {
					String filePath = "/opt/mbank/serviceIcon/";
					dir = new File(filePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					dir = new File(filePath + File.separator + document.getIdentifier());
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
							filePath + "/" + document.getIdentifier() + "." + document.getExtention()));
					stream.write(bytes);
					stream.close();
				} else {
					String rootPath = System.getProperty("catalina.home");
					dir = new File(
							rootPath + File.separator + "WebContent\\" + "images" + File.separator + "serviceIcon");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File serverFile = new File(
							dir.getAbsolutePath() + File.separator + document.getIdentifier() + ".png");
					BufferedOutputStream bufferStream = new BufferedOutputStream(new FileOutputStream(serverFile));
					bufferStream.write(bytes);
					bufferStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void paymentCategoryHandler(MultipartFile file, Document document) {

		if (!file.isEmpty()) {
			File dir;
			try {
				byte[] bytes = file.getBytes();
				if (SystemUtils.IS_OS_LINUX) {
					String filePath = "images" + "/paymentCategoryIcon";
					dir = new File(filePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				} else {
					String rootPath = System.getProperty("catalina.home");
					dir = new File(rootPath + File.separator + "WebContent\\" + "paymentCategoryIcon");
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				File serverFile = new File(
						dir.getAbsolutePath() + File.separator + document.getIdentifier().toString() + ".png");
				BufferedOutputStream bufferStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferStream.write(bytes);
				bufferStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String customerKYCHandler(MultipartFile file) {

		if (!file.isEmpty()) {
			String filename = "" + System.currentTimeMillis();
			try {
				byte[] bytes = file.getBytes();

				String extention = FilenameUtils.getExtension(file.getOriginalFilename());
				String rootPath = System.getProperty("user.dir");
				File dir = new File(rootPath + File.separator + "WebContent\\" + "images/CustomerDocuments/doc/");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File serverFile = new File(dir.getAbsolutePath() + File.separator + filename + "." + extention);
				BufferedOutputStream bufferStream = new BufferedOutputStream(new FileOutputStream(serverFile));
				bufferStream.write(bytes);
				bufferStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return filename;
		}
		return null;
	}

}
