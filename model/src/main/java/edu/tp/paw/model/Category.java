package edu.tp.paw.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="store_categories")
public class Category {

	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "store_categories_category_id_seq" )
	@SequenceGenerator( sequenceName = "store_categories_category_id_seq", name = "store_categories_category_id_seq", allocationSize = 1 )
	@Column( name =  "category_id")
	private Long id;
	@Column( name =  "category_name", length = 100)
	private String name;
	@ManyToOne( fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL )
	@JoinColumn( name = "parent", foreignKey = @ForeignKey(name = "parent_fk"))
	private Category parent;
	
//	@Column( name = "category_path" )
//	private String path;
	
	
	@OneToMany( fetch = FetchType.LAZY, mappedBy = "parent", targetEntity = Category.class, cascade = CascadeType.ALL )
	private Set<Category> children;
	
	@Column( insertable = false, updatable = false )
	private Timestamp created;
	@Column( name =  "last_updated", insertable = false, updatable = true )
	private Timestamp lastUpdated;
	
	/* package */ Category() {
		
	}
	
	/** Creates Category from #{builder}
	 * @param builder The builder with the category
	 */
	/*package*/ Category(final CategoryBuilder builder) {
		this.id = builder.getId();
		this.name = builder.getName();
		this.parent = builder.getParent();
//		this.path = builder.getPath();
		this.children = builder.getChildren();
		this.created = builder.getCreated();
		this.lastUpdated = builder.getLastUpdated();
	}
	

	public void setParent(final Category parent) {
		this.parent = parent;
	}
	

//	public void setPath(final String path) {
//		this.path = path;
//	}
	
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
	public Category getParent() {
		return parent;
	}
	
	public Set<Category> getChildren() {
		return children;
	}

	public Timestamp getCreated() {
		return created;
	}


	public Timestamp getLastUpdated() {
		return lastUpdated;
	}


//	public String getPath() {
//		return path;
//	}
	
	/**
	 * Adds #{category} to #{this} children
	 * @param category The child category
	 */
	public void addChild(final Category category) {
		children.add(category);
	}


	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", parent=" + parent.id
				+ ", children="+ (getChildren()!=null?getChildren().size():0) +"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Category)) {
			return false;
		}
		Category other = (Category) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	
	
}
