package edu.tp.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.tp.paw.interfaces.service.IPurchaseService;
import edu.tp.paw.interfaces.service.IUserService;
import edu.tp.paw.model.Purchase;
import edu.tp.paw.model.PurchaseBuilder;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.User;

@Controller
@RequestMapping("/store/sales")
public class SalesController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(SalesController.class);
	
	@Autowired private IUserService userService;
	@Autowired private IPurchaseService purchaseService;
	
	
	@RequestMapping( value = "/{purchaseId}/approve", method = RequestMethod.POST, produces = "application/json; charset=utf-8" )
	@ResponseBody
	public String approveSale(
			@PathVariable("purchaseId") final long id,
			@ModelAttribute("loggedUser") final User user
		) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return "{\"err\":3 }";
		}
		
		if (userService.approvePurchase(user, purchase)) {
			logger.trace("user {} approved purchase {}", user.getUsername(), purchase.getId());
			return "{\"err\":0 }";
		}
		
		return "{\"err\": 1}";
	}
	
	@RequestMapping(value = "/{purchaseId}/decline", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String declineSale(
			@PathVariable("purchaseId") final long id,
			@ModelAttribute("loggedUser") final User user
		) {
		
		final Purchase purchase = purchaseService.findById(id);
		
		if (purchase == null) {
			return "{\"err\":3 }";
		}
		
		if (userService.declinePurchase(user, purchase)) {
			logger.trace("user {} declined purchase {}", user.getUsername(), purchase.getId());
			return "{\"err\":0 }";
		}
		
		return "{\"err\": 1}";
		
	}
	
}
