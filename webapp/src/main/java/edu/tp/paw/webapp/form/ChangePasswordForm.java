package edu.tp.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ChangePasswordForm {

	@Min(0)
	private long userId;
	
	@Size(min = 4, max = 100, message = "{Size.RegisterForm.password}")
	private String password;
	
	private String oldPassword;
	
	//@Size(min = 4, max = 100)
	private String repeatPassword;
	
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
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "ChangePasswordForm [password=" + password + ", repeatPassword="
				+ repeatPassword + "]";
	}
	
	
	
}
