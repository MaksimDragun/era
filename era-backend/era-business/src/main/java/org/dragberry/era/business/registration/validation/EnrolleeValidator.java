package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class EnrolleeValidator extends AbstractPersonValidator {

	private final static String ERROR_CODE_PREFIX = BASE_ERROR_CODE_PREFIX + "enrollee.";
	
	@Override
	protected String errorPrefix() {
		return ERROR_CODE_PREFIX;
	}

	@Override
	protected String fieldPrefix() {
		return "e";
	}

	@Override
	protected Person getPerson(Registration entity) {
		return entity.getEnrollee();
	}

	@Override
	protected int age() {
		return 15;
	}


}
