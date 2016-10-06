package edu.tp.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController extends BaseController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
}
