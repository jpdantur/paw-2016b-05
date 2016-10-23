package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="store_categories")
public class Category {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "store_categories_category_id_seq" )
	@SequenceGenerator( sequenceName = "store_categories_category_id_seq", name = "store_categories_category_id_seq", allocationSize = 1 )
	@Column( name =  "category_id")
	private final long id;
	@Column( name =  "category_name", length = 100)
	private final String name;
	private long parent;
	
	private String path;
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "id", targetEntity = Category.class )
	private final List<Category> children;
	
	@Column( insertable = false, updatable = false )
	private final Timestamp created;
	@Column( name =  "last_updated", insertable = false, updatable = true )
	private final Timestamp lastUpdated;
	
	/** Creates Category from #{builder}
	 * @param builder The builder with the category
	 */
	/*package*/ Category(CategoryBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.parent = builder.getParent();
		this.path = builder.getPath();
		this.children = builder.getChildren();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
	}
	

	public void setParent(long parent) {
		this.parent = parent;
	}
	

	public void setPath(String path) {
		this.path = path;
	}
	
	// getters

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	/**
	 * gets Category parent id
	 * @return Category parent id
	 */
	public long getParent() {
		return parent;
	}
	
	public List<Category> getChildren() {
		return children;
	}

	public Timestamp getCreated() {
		return created;
	}


	public Timestamp getLastUpdated() {
		return lastUpdated;
	}


	public String getPath() {
		return path;
	}
	
	/**
	 * Adds #{category} to #{this} children
	 * @param category The child category
	 */
	public void addChild(Category category) {
		children.add(category);
	}


	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parent=" + parent
				+ ", path=" + path + ", children=" + children + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (parent ^ (parent >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		if (parent != other.parent)
			return false;
		return true;
	}
	
	
	
	
	
	
}
