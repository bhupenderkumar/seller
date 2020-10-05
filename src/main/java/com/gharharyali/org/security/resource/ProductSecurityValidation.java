package com.gharharyali.org.security.resource;

import com.gharharyali.org.security.AuthoritiesConstants;
import com.gharharyali.org.security.SecurityUtils;
import com.gharharyali.org.service.dto.ProductCriteria;
import com.gharharyali.org.service.dto.ProductDTO;

import io.github.jhipster.service.filter.StringFilter;

public class ProductSecurityValidation {

	/**
	 * Method to apply security based on User Profile
	 * 
	 * @param nurseryDTO
	 */
	public static void productSecurityValidation(ProductDTO productDTO) {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ROLE_ADMIN_SERVER)) {
			productDTO.setUserName((SecurityUtils.getCurrentUserLogin().get()));
		}
	}
	/**
	 * Method to create security for search criteria
	 * @param nurseryDTO
	 */

	public static void productSecurityValidation(ProductCriteria productCriteria) {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ROLE_ADMIN_SERVER)) {
			StringFilter userIdFilter = new StringFilter();
			userIdFilter.setEquals((SecurityUtils.getCurrentUserLogin().get()));
			productCriteria.setUserName(userIdFilter);
		}
	}
}