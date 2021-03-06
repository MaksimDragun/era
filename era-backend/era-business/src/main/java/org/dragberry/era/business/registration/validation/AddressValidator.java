package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator extends AbstractAddressValidator {

	@Override
	protected String errorPrefix() {
		return BASE_ERROR_CODE_PREFIX + "enrollee.address.";
	}

	@Override
	protected String fieldPrefix() {
		return "a";
	}

	@Override
	protected Person getPerson(Registration registration) {
		return registration.getEnrollee();
	}

	@Override
	protected Address getAddress(Registration registration) {
		return registration.getEnrollee().getAddress();
	}

}
