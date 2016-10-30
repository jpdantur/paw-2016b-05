package edu.tp.paw.persistence;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

@Repository
public class StoreImageHibernateDao implements IImageDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public StoreImage findById(final long id) {
		return entityManager.find(StoreImage.class, id);
	}

	@Override
	public Set<StoreImage> imagesForItem(final StoreItem item) {
		
		final StoreItem i = entityManager.getReference(StoreItem.class, item.getId());
		
		// hibernate trick
		i.getImages().iterator();
		
		return i.getImages();
	}

	@Override
	public StoreImage createImage(final StoreImageBuilder builder) {
		
		final StoreImage storeImage = builder.build();
		
		entityManager.persist(storeImage);
		
		return storeImage;
	}
	

}
