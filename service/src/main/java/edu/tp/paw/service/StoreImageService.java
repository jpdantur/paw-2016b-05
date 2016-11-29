package edu.tp.paw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.interfaces.service.IImageService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

@Service
@Transactional
public class StoreImageService implements IImageService {

	@Autowired private IImageDao imageDao;
	@Autowired private IStoreItemService itemService;
	
	@Override
	public StoreImage findById(final long id) {
		return imageDao.findById(id);
	}

	@Override
	public List<StoreImage> imagesForItem(final StoreItem item) {
		if (item == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		if (!itemService.itemExists(item)) {
			throw new IllegalArgumentException("item must exist");
		}
		return imageDao.imagesForItem(item);
	}

	@Override
	public StoreImage uploadImage(final StoreImageBuilder builder) {
		if (builder == null) {
			throw new IllegalArgumentException("image cant be null");
		}
		if (builder.getItem() == null) {
			throw new IllegalArgumentException("item cant be null");
		}
		if (!itemService.itemExists(builder.getItem())) {
			throw new IllegalArgumentException("item cant be null");
		}
		if (builder.getMimeType() == null){
			throw new IllegalArgumentException("mime type cant be null");
		}
		return imageDao.createImage(builder);
	}

}
