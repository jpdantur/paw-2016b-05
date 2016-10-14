package edu.tp.paw.model;

public class RoleBuilder implements IBuilder<Role> {

	private long id;
	
	private final String roleName;
	private final String slug;
	// default is a reserved word
	private boolean _default = false;
	
	public RoleBuilder(final String roleName, final String slug) {
		this.roleName = roleName;
		this.slug = slug;
	}
	
	public RoleBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	public RoleBuilder makeDefault(final boolean _default) {
		this._default = _default;
		return this;
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
	public Role build() {
		return new Role(this);
	}

	
	
}
