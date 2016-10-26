package edu.tp.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table( name = "roles")
public class Role {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "roles_role_id_seq" )
	@SequenceGenerator( sequenceName = "roles_role_id_seq", name = "roles_role_id_seq", allocationSize = 1 )
	@Column( name =  "role_id")
	private long id;
	@Column( name = "role_name", length = 100 )
	private String roleName;
	@Column( name = "role_slug", length = 100 )
	private String slug;
	// default is a reserved word
	@Column( name = "default_role" )
	private boolean _default;
	
	/* package */ Role() {
		// hibernate, duh
	}
	
	/* package */ Role(final RoleBuilder builder) {
		this.id = builder.getId();
		this.roleName = builder.getRoleName();
		this.slug = builder.getSlug();
		this._default = builder.isDefault();
	}

	public long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getSlug() {
		return slug;
	}

	public boolean isDefault() {
		return _default;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((slug == null) ? 0 : slug.hashCode());
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
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		if (id != other.id) {
			return false;
		}
		if (slug == null) {
			if (other.slug != null) {
				return false;
			}
		} else if (!slug.equals(other.slug)) {
			return false;
		}
		return true;
	}
	
	
	
}
