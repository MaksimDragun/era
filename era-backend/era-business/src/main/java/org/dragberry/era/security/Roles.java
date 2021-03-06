package org.dragberry.era.security;

public interface Roles {
	
	interface Registrations {
		String VIEW = "ROLE_REGISTRATIONS_VIEW";
		String CREATE = "ROLE_REGISTRATIONS_CREATE";
		String UPDATE = "ROLE_REGISTRATIONS_UPDATE";
		String DELETE = "ROLE_REGISTRATIONS_DELETE";
		String APPROVE = "ROLE_REGISTRATIONS_APPROVE";
		String CANCEL = "ROLE_REGISTRATIONS_CANCEL";
		String ACCEPT = "ROLE_REGISTRATIONS_ACCEPT";
	}
	
	interface RegistrationPeriods {
		String VIEW = "ROLE_REGISTRATIONPERIODS_VIEW";
	}
	
	interface UserAccounts {
		String VIEW = "ROLE_USERACCOUNTS_VIEW";
		String CREATE = "ROLE_USERACCOUNTS_CREATE";
		String UPDATE = "ROLE_USERACCOUNTS_UPDATE";
		String DELETE = "ROLE_USERACCOUNTS_DELETE";
	}

}
