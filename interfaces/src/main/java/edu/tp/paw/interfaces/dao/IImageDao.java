package edu.tp.paw.interfaces.dao;

import java.util.List;
import java.util.Set;

import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

public interface IImageDao {

	public StoreImage findById(final long id);
	
	public Set<StoreImage> imagesForItem(final StoreItem item);
	
	public StoreImage createImage(final StoreImageBuilder builder);
	
}
