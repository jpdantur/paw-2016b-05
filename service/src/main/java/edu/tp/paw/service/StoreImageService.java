package edu.tp.paw.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.tp.paw.interfaces.service.IImageService;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

@Service
public class StoreImageService implements IImageService {

	@Override
	public StoreImage findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoreImage> imagesForItem(StoreItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoreImage uploadImage(StoreImageBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

}
