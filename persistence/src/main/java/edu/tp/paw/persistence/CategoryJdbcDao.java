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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemBuilder;

@Repository
public class CategoryJdbcDao implements ICategoryDao {

	private static final int ROOT_CATEGORY_ID = 0;
	private static final String CATEGORY_PATH_SEPARATOR = "#";
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Category> rowMapper = (ResultSet resultSet, int rowNum) -> {
		
		return new CategoryBuilder(
					resultSet.getLong("category_id"),
					resultSet.getString("name"),
					resultSet.getLong("parent")
				)
				.created(resultSet.getTimestamp("created"))
				.lastUpdated(resultSet.getTimestamp("last_updated"))
				.path(resultSet.getString("category_path"))
				.build();
		
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
//			return null;
	};
	
	@Autowired
	public CategoryJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("store_categories")
			.usingGeneratedKeyColumns("category_id")
			.usingColumns("name", "parent");
	}
	
	
	@Override
	public List<Category> getDescendants(Category category) {
		
		return jdbcTemplate
		.query(
				"select * from store_categories where category_path like ? order by category_path asc",
				rowMapper,
				category.getPath()+CATEGORY_PATH_SEPARATOR+"%"
		);
		
	}
	
	@Override
	public Category findById(long id) {
		
		List<Category> categoryList =
		jdbcTemplate
		.query(
				"select * from store_categories where category_id = ?",
				rowMapper,
				id);
		
		
		return categoryList.isEmpty() ? null : categoryList.get(0);
	}
	
	private void updatePath(long id, String path) {
		
		System.out.println("dao:: executing path update");
		
		int n = jdbcTemplate.update("update store_categories set category_path = ? where category_id = ?", path, id);
		
		System.out.println("dao:: "+n+" category path updated");
		
	}
	
	@Override
	public Category create(String name, long parent) {
		
		Category parentCategory = null;
		
		System.out.println("dao received request to create category with name = " + name + " parent = " + parent);
		
		if (parent != ROOT_CATEGORY_ID) {
			System.out.println("dao:: parent is not root");
			System.out.println("dao:: finding category = " + parent);
			parentCategory = findById(parent);
			
			
			if (parentCategory == null) {
				System.out.println("dao:: category not found");
				return null;
			} else {
				System.out.println("dao:: category found name = " + parentCategory.getName());
			}
		}
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("parent", parent);
		
		System.out.println("dao:: creating new category");
		
		final Number categoryId = jdbcInsert.executeAndReturnKey(args);
		
		System.out.println("dao:: category created with id = " + categoryId.longValue());
		
		String categoryPath = String.valueOf(categoryId);
		
		if (parentCategory != null) {
			categoryPath = parentCategory.getPath()+CATEGORY_PATH_SEPARATOR+String.valueOf(categoryId);
		}
		
		System.out.println("dao:: updating category path = " + categoryPath);
		
		updatePath(categoryId.longValue(), categoryPath);
		
		System.out.println("dao:: path updated");
		
		return new CategoryBuilder(categoryId.longValue(), name, parent).path(categoryPath).build();
		
	}


	
}
