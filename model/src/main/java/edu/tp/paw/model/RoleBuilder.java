package edu.tp.paw.model;

import java.util.Objects;
import java.util.regex.Pattern;

public class RoleBuilder implements IBuilder<Role> {

	private Long id;
	
	private final String roleName;
	private final String slug;
	// default is a reserved word
	private boolean _default = false;
	
	public RoleBuilder(final String roleName, final String slug) {
		this.roleName = Objects.requireNonNull(roleName);
		this.slug = Objects.requireNonNull(slug);
	}
	
	public RoleBuilder id(final long id) {
		this.id = id;
		return this;
	}
	
	public RoleBuilder makeDefault(final boolean _default) {
		this._default = _default;
		return this;
	}
	
	public Long getId() {
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
		
		Objects.requireNonNull(roleName);
		Objects.requireNonNull(slug);
		
		if (roleName.length() < 2 || roleName.length() > 100) {
			throw new IllegalStateException("role name should be between 2 and 100 chars");
		}
		
		if (slug.length() < 2 || slug.length() > 100 || !Pattern.matches("[A-Za-z0-9_-]+", slug)) {
			throw new IllegalStateException("slug should be between 2 and 100 chars and match [A-Z0-9_-]+");
		}
		
		return new Role(this);
	}

	@Override
	public String toString() {
		return "RoleBuilder [id=" + id + ", roleName=" + roleName + ", slug="
				+ slug + ", _default=" + _default + "]";
	}

	
	
	
}
