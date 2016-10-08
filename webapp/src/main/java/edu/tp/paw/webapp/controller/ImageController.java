package edu.tp.paw.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.tp.paw.interfaces.service.IImageService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.ImageUploadForm;

@Controller
@RequestMapping("/images")
public class ImageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	private IImageService imageService;

	@Autowired
	private IStoreItemService itemService;

	//Download a file
	@RequestMapping(value = "/{imageId}", method = RequestMethod.GET )
	public ResponseEntity<?> downloadFile(@PathVariable("imageId") final long id) {

		final StoreImage image = imageService.findById(id);

		// No file found based on the supplied filename
		if (image == null) {
			return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
		}

		// Generate the http headers with the file properties
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "attachment; filename=" + image.getFilename());

		// Split the mimeType into primary and sub types
		String primaryType, subType;
		try {
			primaryType = image.getMimeType().split("/")[0];
			subType = image.getMimeType().split("/")[1];
		}
		catch (IndexOutOfBoundsException | NullPointerException ex) {
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		headers.setContentType( new MediaType(primaryType, subType) );

		return new ResponseEntity<>(image.getContent(), headers, HttpStatus.OK);
	}

//	@RequestMapping(
//			value = "/{itemId}/upload",
//			method = RequestMethod.POST
//			)
//	public ResponseEntity<?> uploadFile(@PathVariable("itemId") final long id, final MultipartHttpServletRequest request) {
//
//		try {
//			Iterator<String> itr = request.getFileNames();
//
//			while (itr.hasNext()) {
//				final String uploadedFile = itr.next();
//				final MultipartFile file = request.getFile(uploadedFile);
//				final String mimeType = file.getContentType();
//				final String filename = file.getOriginalFilename();
//				final byte[] bytes = file.getBytes();
//
//				final StoreImageBuilder builder = new StoreImageBuilder(filename, mimeType, bytes);
//				final StoreItem item = itemService.fetchById(id);
//				
//				if (item == null) {
//					throw new StoreItemNotFoundException();
//				}
//				
//				builder.item(item);
//				
//				imageService.uploadImage(builder);
//			}
//		}
//		catch (IOException e) {
//			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return new ResponseEntity<>("{}", HttpStatus.OK);
//	}
	
	@RequestMapping(
			value = "/{itemId}/upload",
			method = RequestMethod.POST
			)
	public ResponseEntity<?> uploadFile(
			@PathVariable("itemId") final long id,
			@ModelAttribute("uploadForm") final ImageUploadForm form) {

		logger.debug("received {} files", form.getImages().size());
		
		try {
//			Iterator<String> itr = request.getFileNames();

			for (MultipartFile file : form.getImages()) {
				
				logger.debug("file: {}", file);
				
//				final String uploadedFile = itr.next();
//				final MultipartFile file = request.getFile(uploadedFile);
				final String mimeType = file.getContentType();
				final String filename = file.getOriginalFilename();
				final byte[] bytes = file.getBytes();

				logger.debug("filename: {}", filename);
				
				final StoreImageBuilder builder = new StoreImageBuilder(filename, mimeType, bytes);
				final StoreItem item = itemService.fetchById(id);
				
				if (item == null) {
					throw new StoreItemNotFoundException();
				}
				
				builder.item(item);
				
				imageService.uploadImage(builder);
			}
		}
		catch (IOException e) {
			logger.warn("exception", e);
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}

}
