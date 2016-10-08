package edu.tp.paw.webapp.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.webapp.form.ProfileForm;
import edu.tp.paw.webapp.form.RegisterForm;

@Component
public class ProfileFormValidator implements Validator {

	@Autowired
	private IUserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		final ProfileForm form = (ProfileForm)target;
		
		if (userService.emailExists(form.getEmail())) {
			errors.rejectValue("email", "email", "Email already exists");;
		}
		
	}

	
	
}
