package edu.tp.paw.model;

public class Role {
	
	private final long id;
	private final String roleName;
	private final String slug;
	// default is a reserved word
	private final boolean _default;
	
	public Role(final RoleBuilder builder) {
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
