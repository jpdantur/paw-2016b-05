package edu.tp.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordRecoveryForm {

	@NotEmpty(message = "{NotEmpty.RegisterForm.email}")
	@Email(message = "{Email.RegisterForm.email}")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PasswordRecoveryForm [email=" + email + "]";
	}
	
	
	
}
