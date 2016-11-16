package edu.tp.paw.webapp.auth;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import edu.tp.paw.interfaces.service.IStoreItemService;
import edu.tp.paw.model.StoreItem;
import edu.tp.paw.model.StoreItemStatus;

@Component
public class ItemOwnerBasedVoter implements AccessDecisionVoter<FilterInvocation> {

	private static final String EDIT_ITEM_SUFFIX = "/details";
	private static final String STORE_ITEMS_PREFIX = "/store/item/";
	private static final String STORE_ITEM_PREVIEW_PREFIX = "/store/items/";
	private static final String STORE_ITEM_PREVIEW_SUFFIX = "/all";
	private final static Logger logger = LoggerFactory.getLogger(ItemOwnerBasedVoter.class); 
	
	@Autowired private IStoreItemService itemService;
	
	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(
			final Authentication authentication,
			final FilterInvocation invocation,
			final Collection<ConfigAttribute> attributes
		) {
		
		logger.trace("attribs: {}", attributes);
		
		logger.trace("invocation: {}", invocation.getRequestUrl());
		
		final Object principal = authentication.getPrincipal();
		
		final String username;
		
		if (principal instanceof String) {
			username = (String)principal;
			logger.trace("principal(String): {}", (String)principal);
		} else if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
			logger.trace("principal(UserDetails): {}", ((UserDetails)principal).getUsername());
		} else {
			username = principal.toString();
			logger.trace("principal: {}", principal.toString());
		}
		
		final StringBuilder stringBuilder = new StringBuilder(invocation.getRequestUrl());
		
		logger.trace("url: {}", stringBuilder.toString());
		
		final boolean isEditing =
				invocation.getRequestUrl().startsWith(STORE_ITEMS_PREFIX) &&
				invocation.getRequestUrl().contains(EDIT_ITEM_SUFFIX);
		
		final boolean isPreviewing =
				invocation.getRequestUrl().startsWith(STORE_ITEM_PREVIEW_PREFIX) &&
				!invocation.getRequestUrl().contains(STORE_ITEM_PREVIEW_SUFFIX);
		
		if (isEditing || isPreviewing) {
			
			if (isEditing) {
				stringBuilder.delete(0, STORE_ITEMS_PREFIX.length());
			} else {
				stringBuilder.delete(0, STORE_ITEM_PREVIEW_PREFIX.length());
			}
			
			logger.trace("url: {}", stringBuilder.toString());
			
			final String[] requestUrlParts = stringBuilder.toString().split("[/?]");
			final int itemId = Integer.valueOf(requestUrlParts[0]); 
			
			logger.trace("itemId is {}", itemId);
			
			final StoreItem item = itemService.findById(itemId);
			
			if (item == null) {
				return ACCESS_ABSTAIN;
			}
			
			logger.trace("item owner is {}", item.getOwner().getUsername());
			
			if (isEditing) {
			
				if (item.getOwner().getUsername().equals(username)) {
					return ACCESS_GRANTED;
				}
			} else {
				if (item.getStatus() == StoreItemStatus.UNPUBLISHED || item.getStatus() == StoreItemStatus.PAUSED) {
					if (!item.getOwner().getUsername().equals(username)) {
						return ACCESS_DENIED;
					}
				}
				return ACCESS_GRANTED;
			}
			return ACCESS_DENIED;
		}
		
		return ACCESS_GRANTED;
	}

}
