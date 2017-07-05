package edu.tp.paw.interfaces.service;

import java.util.List;

import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

public interface IImageService {

	public StoreImage findById(final long id);
	
	public List<StoreImage> imagesForItem(final StoreItem item);
	
	public StoreImage uploadImage(final StoreImageBuilder builder);
	
	public void removeImagesForItem(final StoreItem item);
	
}
