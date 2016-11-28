package edu.tp.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IPurchaseDao;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseReview;
import edu.tp.paw.model.PurchaseReviewBuilder;
import edu.tp.paw.model.PurchaseStatus;
import edu.tp.paw.model.User;

@Repository
public class PurchaseHibernateDao implements IPurchaseDao {

	private final static Logger logger = LoggerFactory.getLogger(PurchaseHibernateDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Purchase findById(final long id) {
		return entityManager.find(Purchase.class, id);
	}

	@Override
	public boolean updatePurchaseStatus(final Purchase purchase, final PurchaseStatus status) {
		final Query query = entityManager.createQuery("update Purchase set status = :status where id = :id");
		query.setParameter("id", purchase.getId());
		query.setParameter("status", status);
		return query.executeUpdate() == 1;
	}

	@Override
	public boolean updatePurchaseBuyerReview(final Purchase purchase, final PurchaseReview review) {
		
		final Query query = entityManager.createQuery("update Purchase set buyerReview = :review where id = :id");
		query.setParameter("id", purchase.getId());
		query.setParameter("review", review);
		return query.executeUpdate() == 1;
		
	}

	@Override
	public boolean updatePurchaseSellerReview(final Purchase purchase, final PurchaseReview review) {
		
		final Query query = entityManager.createQuery("update Purchase set sellerReview = :review where id = :id");
		query.setParameter("id", purchase.getId());
		query.setParameter("review", review);
		return query.executeUpdate() == 1;
	}

	@Override
	public PurchaseReview createPurchaseReview(final PurchaseReviewBuilder builder) {
		
		final PurchaseReview review = builder.build();
		
		logger.debug("persisting {}", review);
		
		entityManager.persist(review);
		
		entityManager.flush();
		
		return review;
	}

	@Override
	public float getAverageAsBuyer(final User user) {
		final TypedQuery<Double> query = entityManager.createQuery("select round(coalesce(avg(p.sellerReview.rating),0), 1) from Purchase p where p.buyer = :user", Double.class);
		query.setParameter("user", user);
		return query.getSingleResult().floatValue();
	}

	@Override
	public float getAverageAsSeller(final User user) {
		final TypedQuery<Double> query = entityManager.createQuery("select round(coalesce(avg(p.buyerReview.rating),0), 1) from Purchase p where p.item.owner = :user", Double.class);
		query.setParameter("user", user);
		return query.getSingleResult().floatValue();
	}

}
