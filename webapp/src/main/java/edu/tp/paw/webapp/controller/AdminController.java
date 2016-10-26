package edu.tp.paw.webapp.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tp.paw.interfaces.service.ICategoryService;
import edu.tp.paw.interfaces.service.IRoleService;
import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Category;
import edu.tp.paw.model.CategoryBuilder;
import edu.tp.paw.model.Role;
import edu.tp.paw.model.RoleBuilder;
import edu.tp.paw.model.User;
import edu.tp.paw.model.UserBuilder;
import edu.tp.paw.webapp.exceptions.CategoryNotFoundException;
import edu.tp.paw.webapp.exceptions.StoreItemNotFoundException;
import edu.tp.paw.webapp.form.CategoryForm;
import edu.tp.paw.webapp.form.CreateUserForm;
import edu.tp.paw.webapp.form.RoleForm;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired private IStoreItemService storeItemService;
	@Autowired private ICategoryService categoryService;
	@Autowired private IUserService userService;
	@Autowired private IRoleService roleService;
	
	
	@RequestMapping({"","/dashboard"})
	public String index(final Model model) {
		
		model.addAttribute("numberOfItems", storeItemService.getNumberOfItems());
		model.addAttribute("numberOfCategories", categoryService.getNumberOfCategories());
		model.addAttribute("numberOfUsers", userService.getNumberOfUsers());
		
		
		return "admin";
	}
	
	@RequestMapping("/categories")
	public String categories(final Model model) {
		
		model.addAttribute("categories", categoryService.getCategoryTree());
		
		return "admin_categories";
	}
	
	@RequestMapping( value = "/categories/create", method = RequestMethod.GET)
	public String createCategory(
			@ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "create_category";
	}
	
	@RequestMapping( value = "/categories/create", method = RequestMethod.POST)
	public String createCategorySubmit(
			@Valid @ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		logger.debug("form is {}", form);
		
		if (!result.hasErrors()) {
		
			final CategoryBuilder builder = new CategoryBuilder(form.getName(), categoryService.findById(form.getParent()));
			categoryService.create(builder);
			
			model.addAttribute("success", true);	
			return "create_category";
		}
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "create_category";
	}
	
	@RequestMapping( value = "/categories/{categoryId}/edit", method = RequestMethod.GET)
	public String editCategory(
			@PathVariable("categoryId") final long id,
			@ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		final Category category = categoryService.findById(id);
		
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		
		model.addAttribute("category", category);
		model.addAttribute("result", result);
		model.addAttribute("update", true);
		
		return "edit_category";
	}
	
	@RequestMapping( value = "/categories/{categoryId}/edit", method = RequestMethod.POST)
	public String editCategorySubmit(
			@PathVariable("categoryId") final long id,
			@Valid @ModelAttribute("createCategoryForm") final CategoryForm form,
			final BindingResult result,
			final Model model
			) {
		
		logger.debug("form is {}", form);
		
		if (!result.hasErrors()) {
		
			final Category category = new CategoryBuilder(form.getName(), categoryService.findById(form.getParent())).id(id).build();
			
			if (categoryService.updateCategory(category)) {
				model.addAttribute("success", true);	
				return "edit_category";
			}
			
			
		}
		
		//TODO check if parent exists
		
		model.addAttribute("category", form);
		model.addAttribute("result", result);
		
		return "edit_category";
	}
	
	@RequestMapping("/users")
	public String users(final Model model) {
		
		model.addAttribute("users", userService.getAllUsers());
		
		
		return "admin_users";
	}
	
	@RequestMapping( value = "/users/create", method = RequestMethod.GET)
	public String usersCreate(
			@ModelAttribute("createRoleForm") final CreateUserForm form,
			final BindingResult result,
			final Model model) {
		
		model.addAttribute("user", form);
		model.addAttribute("result", result);
		model.addAttribute("roles", roleService.getRoles());
		
		return "admin_users_create";
	}
	
	@RequestMapping( value = "/users/create", method = RequestMethod.POST)
	public String usersCreateSubmit(
			@Valid @ModelAttribute("createRoleForm") final CreateUserForm form,
			final BindingResult result,
			final Model model) {
		
		if (!result.hasErrors()) {
			
			final UserBuilder builder = new UserBuilder(
					form.getUsername()
				)
				.firstName(form.getFirstName())
				.lastName(form.getLastName())
				.email(form.getEmail())
				.password(form.getPassword());
			
			final Role role = roleService.findRoleById(form.getRole());
			userService.createUser(builder, role);
			
			return "redirect:/admin/users";
		}
		
		model.addAttribute("user", form);
		model.addAttribute("result", result);
		model.addAttribute("roles", roleService.getRoles());
		
		return "admin_users_create";
	}
	
	@RequestMapping( value = "/users/{userId}/roles", method = RequestMethod.GET)
	public String userRoles(
			@PathVariable("userId") final long id,
			@RequestParam( value = "s", required = false) final boolean success,
			@RequestParam( value = "e", required = false) final boolean error,
			final Model model) {
		
		final User user = userService.findById(id);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
		final Set<Role> roles = userService.getRoles(user);
		
		model.addAttribute("user", user);
		model.addAttribute("roles", roles);
		model.addAttribute("allRoles", roleService.getRoles());
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		
		return "admin_users_roles";
	}
	
	@RequestMapping( value = "/users/{userId}/roles", method = RequestMethod.POST)
	public String userRolesAdd(
			@PathVariable("userId") final long id,
			@RequestParam("role") final long roleId, 
			final Model model) {
		
		final User user = userService.findById(id);
		
		if (user == null) {
			throw new StoreItemNotFoundException();
		}
		
		final Role role = roleService.findRoleById(roleId);
		
		if (userService.addRole(user, role)) {
			
			return "redirect:/admin/users/"+user.getId()+"/roles?s=1";
			
		} else {
			
			return "redirect:/admin/users/"+user.getId()+"/roles?e=1";
		}
	}
	
	@RequestMapping("/roles")
	public String roles(final Model model) {
		
		model.addAttribute("roles", roleService.getRoles());
		
		
		return "admin_roles";
	}
	
	@RequestMapping( value = "/roles/create", method = RequestMethod.GET)
	public String roles(
			@ModelAttribute("createRoleForm") final RoleForm form,
			final BindingResult result,
			final Model model) {
		
		model.addAttribute("role", form);
		model.addAttribute("result", result);
		
		return "admin_roles_create";
	}
	
	@RequestMapping( value = "/roles/create", method = RequestMethod.POST)
	public String rolesSubmit(
			@Valid @ModelAttribute("createRoleForm") final RoleForm form,
			final BindingResult result,
			final Model model) {
		
		if (!result.hasErrors()) {
			
			final RoleBuilder roleBuilder = new RoleBuilder(form.getName(), form.getSlug());
			roleService.createRole(roleBuilder);
			
			return "redirect:/admin/roles";
		}
		
		model.addAttribute("role", form);
		model.addAttribute("result", result);
		
		return "admin_roles_create";
	}
	
	@RequestMapping( value =  "/roles/default/{roleId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String makeDefault(
			@PathVariable("roleId") final long id
			) {
		
		final Role role = roleService.findRoleById(id);
		
		logger.trace("role {} will be made default", role);
		
		if ( roleService.makeDefault(role) ) {
			return "{\"err\":0}";
		}
		
		return "{\"err\":1}";
		
	}
	
	

}
