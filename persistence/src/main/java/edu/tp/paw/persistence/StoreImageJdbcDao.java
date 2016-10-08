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

import edu.tp.paw.interfaces.dao.IImageDao;
import edu.tp.paw.interfaces.dao.IStoreItemDao;
import edu.tp.paw.model.StoreImage;
import edu.tp.paw.model.StoreImageBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;

@Repository
public class StoreImageJdbcDao implements IImageDao {

	@Autowired
	private IStoreItemDao itemDao;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final RowMapper<StoreImage> rowMapper = (ResultSet resultSet, int rowNum) -> {
		final StoreImage storeImage = 
				new StoreImageBuilder(
						resultSet.getString("filename"),
						resultSet.getString("mime_type"),
						resultSet.getBytes("content"))
			.item(itemDao.findById(resultSet.getLong("item_id")))
			.id(resultSet.getLong("image_id"))
			.build();
				
		return storeImage;
	};
	
	@Autowired
	public StoreImageJdbcDao(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("images")
			.usingGeneratedKeyColumns("image_id");
	}
	
	@Override
	public StoreImage findById(final long id) {
		List<StoreImage> imageList =
				jdbcTemplate
				.query(
						"select * from images where image_id = ?",
						rowMapper,
						id);
				
		return imageList.isEmpty() ? null : imageList.get(0);
	}

	@Override
	public List<StoreImage> imagesForItem(final StoreItem item) {
		return
				jdbcTemplate
				.query(
						"select * from images where item_id = ?",
						rowMapper,
						item.getId());
	}

	@Override
	public StoreImage createImage(StoreImageBuilder builder) {
		
		final Map<String, Object> args = new HashMap<>();
		
		args.put("item_id", builder.getItem().getId());
		args.put("content", builder.getContent());
		args.put("mime_type", builder.getMimeType());
		args.put("filename", builder.getFilename());
		
		final Number imageId = jdbcInsert.executeAndReturnKey(args);
		
		return builder.id(imageId.longValue()).build();
	}

}
