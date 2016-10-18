package edu.tp.paw.webapp.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.webapp.form.ChangePasswordForm;
import edu.tp.paw.webapp.form.ProfileForm;
import edu.tp.paw.webapp.form.RegisterForm;

@Component
public class ChangePasswordFormValidator implements Validator {

	@Autowired
	private IUserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		final ChangePasswordForm form = (ChangePasswordForm)target;
		
		final User user = userService.findById(form.getUserId());
		
		if (user == null) {
			errors.rejectValue("userId", "error.user", "No such user");
		}
		
		if (!userService.isUsersPassword(user, form.getOldPassword())) {
			errors.rejectValue("oldPassword", "WrongPassword.ChangePasswordForm.oldPassword", "Not your password");
		}
		
		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "PasswordMatch.ChangePasswordForm.repeatPassword", "Passwords do not match");
		}
		
	}

	
	
}
