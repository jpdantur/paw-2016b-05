package edu.tp.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class ProfileForm {

	@NotEmpty(message = "{NotEmpty.ProfileForm.email}")
	@Email(message="{Email.ProfileForm.email}")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ProfileForm [email=" + email + "]";
	}
	
}
