package com.eter.auth.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;

public class UserAuditAware implements AuditorAware<String> {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getCurrentAuditor() {
		return null;
	}

}
