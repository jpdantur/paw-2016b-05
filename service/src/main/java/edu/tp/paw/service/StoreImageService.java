package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.interfaces.service.IImageService;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

@Service
public class StoreImageService implements IImageService {

	@Autowired
	private IImageDao imageDao;
	
	@Override
	public StoreImage findById(final long id) {
		return imageDao.findById(id);
	}

	@Override
	public List<StoreImage> imagesForItem(final StoreItem item) {
		return imageDao.imagesForItem(item);
	}

	@Override
	public StoreImage uploadImage(final StoreImageBuilder builder) {
		return imageDao.createImage(builder);
	}

}
