package edu.tp.paw.interfaces.dao;

import java.util.List;

import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

public interface IImageDao {

	public StoreImage findById(final long id);
	
	public List<StoreImage> imagesForItem(final StoreItem item);
	
	public StoreImage createImage(final StoreImageBuilder builder);
	
}
