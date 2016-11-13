package edu.tp.paw.interfaces.service;

import java.util.Set;

import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

public interface IImageService {

	public StoreImage findById(final long id);
	
	public Set<StoreImage> imagesForItem(final StoreItem item);
	
	public StoreImage uploadImage(final StoreImageBuilder builder);
	
}
