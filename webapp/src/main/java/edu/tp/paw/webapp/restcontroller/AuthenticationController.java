package edu.tp.paw.webapp.restcontroller;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.webapp.auth.TokenHelper;
import edu.tp.paw.webapp.dto.JWTTokenDTO;
import edu.tp.paw.webapp.dto.LoginDTO;
import edu.tp.paw.webapp.dto.UserDTO;
import edu.tp.paw.webapp.form.PasswordRecoveryForm;
import edu.tp.paw.webapp.form.RegisterForm;
import io.jsonwebtoken.Claims;

@Path("/auth")
@Component
public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired private IUserService userService;
	
	@Autowired private TokenHelper tokenHelper;
	
	@Context private UriInfo uriInfo;
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(final LoginDTO login) {
		
		logger.trace(login.toString());
		
		final User user = userService.findByUsername(login.getUsername());
		
		if (user == null) {
			logger.trace("username not found");
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		if (userService.isUsersPassword(user, login.getPassword())) {
			final String idToken = tokenHelper.buildToken(user);
			final String refreshToken = tokenHelper.createRefreshToken();
			
			final User updatedUser = new UserBuilder(user).refreshToken(refreshToken).build();
			
			if (userService.updateUser(updatedUser)) {
				return Response.ok(JWTTokenDTO.createWithTokens(idToken, refreshToken)).build();
			} else {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
			
		}
		
		logger.trace("password does not match");
		
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/renew")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response renew(final JWTTokenDTO tokens) {
		
		Claims claims = tokenHelper.decodeToken(tokens.getIdToken());
		
		logger.trace(tokens.toString());
		
		if (claims == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		logger.trace(claims.toString());
		
		final User user = userService.findByUsername(claims.getSubject());
		
		if (user == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		 
		if (user.getRefreshToken().equals(tokens.getRefreshToken())) {
			return Response.ok(JWTTokenDTO.createWithTokens(tokenHelper.buildToken(user), user.getRefreshToken())).build();
		}
		
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(@NotNull @Valid final RegisterForm form) throws ValidationException {
		
		logger.trace(form.toString());
		
		UserBuilder userBuilder = null;
		
		try {
			userBuilder = new UserBuilder(form.getUsername())
			.firstName(form.getFirstName())
			.lastName(form.getLastName())
			.email(form.getEmail())
			.password(form.getPassword())
			.refreshToken(tokenHelper.createRefreshToken());
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		try {
			logger.trace(userBuilder.toString());
			
			final User u = userService.registerUser(userBuilder);
			
			final URI uri = uriInfo.getAbsolutePathBuilder().path(String.format("/api/users/%d", u.getId())).build();
			
			return Response.created(uri).entity(JWTTokenDTO.createWithTokens(tokenHelper.buildToken(u), u.getRefreshToken())).build();
		} catch (Exception e) {
			return Response.status(Status.CONFLICT).build();
		}
	}
	
	@POST
	@Path("/forgot-pass")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPass(final PasswordRecoveryForm form) {
		
		logger.trace(form.toString());
		
		if (userService.emailExists(form.getEmail())) {
			
			final User user = userService.findByEmail(form.getEmail());
			
			userService.recoverPassword(user);
			
			return Response.ok().build();
		}
		
		return Response.status(Status.BAD_REQUEST).build();
	}
}
