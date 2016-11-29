package edu.tp.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;

@Repository
public class StoreImageHibernateDao implements IImageDao {

//	private final static Logger logger = LoggerFactory.getLogger(StoreImageHibernateDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public StoreImage findById(final long id) {
		return entityManager.find(StoreImage.class, id);
	}

	@Override
	public List<StoreImage> imagesForItem(final StoreItem item) {
		
		final TypedQuery<StoreImage> query = entityManager.createQuery("from StoreImage si where item=:item", StoreImage.class);
		query.setParameter("item", item);
		return query.getResultList();
		
//		final StoreItem i = entityManager.getReference(StoreItem.class, item.getId());
//		
//		logger.debug("item images: {}", i.getImages());
//		
//		// hibernate trick
//		i.getImages().iterator();
//		i.getImages().size();
//		
//		logger.debug("item images: {}", i.getImages());
//		
//		return i.getImages();
	}

	@Override
	public StoreImage createImage(final StoreImageBuilder builder) {
		
		final StoreImage storeImage = builder.build();
		
		entityManager.persist(storeImage);
		
		return storeImage;
	}
	

}
