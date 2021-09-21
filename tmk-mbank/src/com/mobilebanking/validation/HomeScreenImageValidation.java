package com.mobilebanking.validation;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HomeScreenImageValidation {

	public String validateHomeScreenImage(MultipartFile file) {
		if (file == null || file.isEmpty() || !(FilenameUtils.getExtension(file.getOriginalFilename()).equals("jpg"))) {
			return "Please select a jpg image with resolution of 1024x500.";
		} else {
			try {
				BufferedImage image = ImageIO.read(file.getInputStream());
				Integer width = image.getWidth();
				Integer height = image.getHeight();
				if (width != 1024 || height != 500) {
					return "Please select a jpg image with resolution of 1024x500.";
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "Something went wrong. Please try again later.";
			}
		}
		return null;
	}

}
