package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.IStoreDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;


@Repository
public class StoreJdbcDao implements IStoreDao {

	@Autowired
	private CategoryJdbcDao categoryDao;
	
	@Autowired
	private StoreItemJdbcDao storeItemDao;

	@Override
	public List<StoreItem> findInCategory(Category category) {
		
		List<Category> categories = categoryDao.getDescendants(category);
		
		categories.add(category);
		
		return storeItemDao.findInCategories(categories);
	}
	
}
