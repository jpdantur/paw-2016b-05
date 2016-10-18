package edu.tp.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CreateUserForm {

	@Size(min = 2, max = 100, message = "{Size.RegisterForm.firstName}")
	private String firstName;
	@Size(min = 2, max = 100, message = "{Size.RegisterForm.lastName}")
	private String lastName;
	@Size(min = 6, max = 100, message = "{Size.RegisterForm.username}")
	@Pattern(regexp = "[a-zA-Z0-9_-]+", message = "{Pattern.RegisterForm.username}")
	private String username;
	@Size(min = 4, max = 100, message = "{Size.RegisterForm.password}")
	private String password;
	//@Size(min = 4, max = 100, message = "{Size.RegisterForm.firstName}")
	private String repeatPassword;
	@NotEmpty(message = "{NotEmpty.RegisterForm.email}")
	@Email(message = "{Email.RegisterForm.email}")
	private String email;
	
	@Min(0)
	private long role;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public long getRole() {
		return role;
	}
	public void setRole(long role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterForm [firstName=" + firstName + ", lastName=" + lastName
				+ ", username=" + username + ", password=" + password
				+ ", repeatPassword=" + repeatPassword + ", email=" + email + "]";
	}
	
	
	
}
