package edu.tp.paw.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IPurchaseDao;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseStatus;

@Repository
public class PurchaseHibernateDao implements IPurchaseDao {

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
		query.executeUpdate();
		return true;
	}

}
