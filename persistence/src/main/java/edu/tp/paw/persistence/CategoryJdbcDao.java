package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

@Repository
public class CategoryJdbcDao implements ICategoryDao {

	private static final String CATEGORY_PATH_SEPARATOR = "#";
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Category> rowMapper = (ResultSet resultSet, int rowNum) -> {
//		return new StoreItemBuilder(
//				resultSet.getInt("item_id"),
//				resultSet.getString("name"),
//				resultSet.getString("description"),
//				resultSet.getFloat("price")
//			)
//			.created(resultSet.getTimestamp("created"))
//			.lastUpdated(resultSet.getTimestamp("last_updated"))
//			.sold(resultSet.getInt("sold"))
//			.build();
			return null;
	};
	
	@Autowired
	public CategoryJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("store_items")
			.usingGeneratedKeyColumns("item_id")
			.usingColumns("name", "description", "price");
		
		
//		jdbcTemplate.execute(
//			"create table if not exists store_items ("
//				+ "item_id serial primary key,"
//				+ "name varchar(100),"
//				+ "description text,"
//				+ "sold integer default 0,"
//				+ "price real,"
//				+ "created timestamp default current_timestamp,"
//				+ "last_updated timestamp default current_timestamp"
//			+ ")"
//		);
	}
	
	private static boolean isSubcategoryOf(final Category assumedParent, final Category child) {
		return child.getPath().startsWith(assumedParent.getPath());
	}
	
	private Category assembleCategoryTreeRecursive(Category category, Iterator<Category> it) {
		
		
		
		
			
//			Category lastUnresolvedCategory = 
			
			
			
//		}
		
		return null;
	}
	
	private Category assembleCategoryTree(final Category category) {
		
		final List<Category> categoryList = 
				jdbcTemplate
				.query(
						"select * from store_category where path like ? order by path asc",
						rowMapper,
						category.getPath()+CATEGORY_PATH_SEPARATOR
				);
		
		Category currentParent = category;
		
		for (Category currentCategory : categoryList) {
			
			if ( isSubcategoryOf(currentParent, currentCategory) ) {
				currentCategory.setParent(currentParent);
				currentParent.addChild(currentCategory);
				
				currentParent = currentCategory;
				
			} else {
				
				Category cat = category.getParent();
				
				while (cat != null) {
					
					if ( isSubcategoryOf(cat, currentCategory) ) {
						
						cat.addChild(currentCategory);
						currentCategory.setParent(cat);
						
						currentParent = cat;
						
						cat = null; // break;
					}
					
					cat = cat.getParent();
				}
			}
		}
		
		
		return category;
	}
	
	@Override
	public Category findById(long id) {
		
		List<Category> categoryList =
		jdbcTemplate
		.query(
				"select * from store_category where category_id = ?",
				rowMapper,
				id);
		
		if (categoryList.isEmpty()) {
			return null;
		}
		
		return assembleCategoryTree(categoryList.get(0));
	}
	
	//@Override
	public StoreItem create(final String name, final String description, final float price) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("description", description);
		args.put("price", price);
		
		final Number storeItemId = jdbcInsert.executeAndReturnKey(args);
		
		return new StoreItemBuilder(storeItemId.longValue(), name, description, price).build();
		
	}

	@Override
	public Category create(String name, long parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
