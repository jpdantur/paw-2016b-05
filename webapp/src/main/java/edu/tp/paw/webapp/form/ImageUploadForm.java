package edu.tp.paw.webapp.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadForm {

	private List<MultipartFile> images = new ArrayList<>();

	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}
	
	public void addImages(List<MultipartFile> images) {
		this.images.addAll(images);
	}
	
}
