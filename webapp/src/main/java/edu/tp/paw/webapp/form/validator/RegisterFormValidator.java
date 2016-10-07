package edu.tp.paw.webapp.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.webapp.form.RegisterForm;

@Component
public class RegisterFormValidator implements Validator {

	@Autowired
	private IUserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		final RegisterForm form = (RegisterForm)target;
		
		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("repeatPassword", "repeatPassword", "Passwords should match");;
		}
		
		if (userService.usernameExists(form.getUsername())) {
			errors.rejectValue("username", "username", "Username already exists");;
		}
		
		if (userService.emailExists(form.getEmail())) {
			errors.rejectValue("email", "email", "Email already exists");;
		}
		
	}

	
	
}
