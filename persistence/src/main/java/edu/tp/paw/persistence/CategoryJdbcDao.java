package edu.tp.paw.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import edu.tp.paw.interfaces.dao.ICategoryDao;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;

@Repository
public class CategoryJdbcDao implements ICategoryDao {

	private static final String SQL_DESCENDANTS_OF =
			"with recursive tree as "
			+ "( "
			+ "select category_id, name, created, last_updated, parent, (0 || '#' || cast (category_id as text)) as category_path "
			+ "from store_categories "
			+ "where category_id <> ? and parent = ? "
			+ "union all "
				+ "select c.category_id, c.name, c.created, c.last_updated, c.parent, (c2.category_path || '#' || cast (c.category_id as text)) as category_path "
				+ "from store_categories as c "
				+ "inner join tree as c2 on (c.parent=c2.category_id) where c.category_id <> 0 "
			+ ") select * from tree order by name";
	
	//name::text as fullname
	//(c2.fullname || '~>' || c.name)::text as fullname
	
//	private static final String CATEGORY_PATH_SEPARATOR = "#";
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Category> rowMapperWithPath = (ResultSet resultSet, int rowNum) -> {
		
		return new CategoryBuilder(
					resultSet.getString("name"),
					resultSet.getLong("parent")
				)
				.id(resultSet.getLong("category_id"))
				.created(resultSet.getTimestamp("created"))
				.lastUpdated(resultSet.getTimestamp("last_updated"))
				.path(resultSet.getString("category_path"))
				.build();
	};
	
	private final static RowMapper<Category> rowMapper = (ResultSet resultSet, int rowNum) -> {
		
		return new CategoryBuilder(
					resultSet.getString("name"),
					resultSet.getLong("parent")
				)
				.id(resultSet.getLong("category_id"))
				.created(resultSet.getTimestamp("created"))
				.lastUpdated(resultSet.getTimestamp("last_updated"))
				.build();
	};
	
	/**
	 * Creates a new CategoryJdbcDao for #{dataSource}
	 * @param dataSource The given dataSource
	 */
	@Autowired
	public CategoryJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("store_categories")
			.usingGeneratedKeyColumns("category_id")
			.usingColumns("name", "parent");
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#getDescendants(edu.tp.paw.model.Category)
	 */
	@Override
	public List<Category> getDescendants(Category category) {
		
//		return jdbcTemplate
//		.query(
//				"select * from store_categories where category_path like ? rder by category_path asc",
//				rowMapper,
//				category.getPath()+CATEGORY_PATH_SEPARATOR+"%"
//		);
		
		return jdbcTemplate
				.query(
						SQL_DESCENDANTS_OF,
						rowMapperWithPath,
						category.getId(),
						category.getId()
				);
		
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#findById(long)
	 */
	@Override
	public Category findById(long id) {
		
		new Throwable().printStackTrace();
		
		List<Category> categoryList =
		jdbcTemplate
		.query(
				"select * from store_categories where category_id = ?",
				rowMapper,
				id);
		
		
		return categoryList.isEmpty() ? null : categoryList.get(0);
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#categoryExists(long)
	 */
	@Override
	public boolean categoryExists(long id) {
		
		return findById(id) == null ? false : true;
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#updatePath(edu.tp.paw.model.Category, java.lang.String)
	 */
	@Override
	public Category updatePath(Category category, String path) {
		
		jdbcTemplate.update("update store_categories set category_path = ? where category_id = ?", path, category.getId());
		
		category.setPath(path);
		
		return category;
		
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#create(java.lang.String, long)
	 */
	@Override
	public Category create(String name, long parent) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", name);
		args.put("parent", parent);
		
		System.out.println("dao:: creating new category");
		
		final Number categoryId = jdbcInsert.executeAndReturnKey(args);
		
		return new CategoryBuilder(name, parent).id(categoryId.longValue()).build();		
		
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#create(java.lang.String, long)
	 */
	@Override
	public Category create(CategoryBuilder builder) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("name", builder.getName());
		args.put("parent", builder.getParent());
		
		System.out.println("dao:: creating new category");
		
		final Number categoryId = jdbcInsert.executeAndReturnKey(args);
		
		return builder.id(categoryId.longValue()).build();
		
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#getSiblings(edu.tp.paw.model.Category)
	 */
	@Override
	public List<Category> getSiblings(Category category) {
		
		return
				jdbcTemplate
				.query(
						"select * from store_categories where parent = ?",
						rowMapper,
						category.getParent());
	}


	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#getChildren(long)
	 */
	@Override
	public List<Category> getChildren(long categoryId) {
		
		System.out.println("querying getChildren with: " + categoryId);
		
		return
				jdbcTemplate
				.query(
						"select * from store_categories where parent = ? and category_id <> 0",
						rowMapper,
						categoryId);
	}
	
	/* (non-Javadoc)
	 * @see edu.tp.paw.interfaces.dao.ICategoryDao#getChildren(edu.tp.paw.model.Category)
	 */
	@Override
	public List<Category> getChildren(Category category) {
		
		return getChildren(category.getId());
	}

	@Override
	public List<Category> getAll() {
		return
				jdbcTemplate
				.query(
						"select * from store_categories where category_id <> 0 order by name asc",
						rowMapper);
	}

}
