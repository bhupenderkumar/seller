package com.gharharyali.org.security.resource;

import com.gharharyali.org.security.AuthoritiesConstants;
import com.gharharyali.org.security.SecurityUtils;
import com.gharharyali.org.service.dto.NurseryCriteria;
import com.gharharyali.org.service.dto.NurseryDTO;

import io.github.jhipster.service.filter.StringFilter;

public class NurserySecurityValidation {

	/**
	 * Method to apply security based on User Profile
	 * 
	 * @param nurseryDTO
	 */
	public static void nurserySecurityValidation(NurseryDTO nurseryDTO) {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ROLE_ADMIN_SERVER)) {
			nurseryDTO.setUserName(SecurityUtils.getCurrentUserLogin().get());
		}
	}
	/**
	 * Method to create security for search criteria
	 * @param nurseryDTO
	 */

	public static void nurserySecurityValidation(NurseryCriteria nurseryDTO) {
		if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ROLE_ADMIN_SERVER)) {
			StringFilter userIdFilter = new StringFilter();
			userIdFilter.setEquals((SecurityUtils.getCurrentUserLogin().get()));
			nurseryDTO.setUserName(userIdFilter);
		}
	}
}