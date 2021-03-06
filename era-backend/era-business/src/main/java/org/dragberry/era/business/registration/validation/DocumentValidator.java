package org.dragberry.era.business.registration.validation;

import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;
import org.springframework.stereotype.Component;

@Component
public class DocumentValidator extends AbstractDocumentValidator {

	private final static String ERROR_CODE_PREFIX = BASE_ERROR_CODE_PREFIX + "enrollee.document.";
	
	@Override
	protected String errorPrefix() {
		return ERROR_CODE_PREFIX;
	}

	@Override
	protected String fieldPrefix() {
		return "d";
	}
	
	@Override
	protected Person getPerson(Registration registration) {
		return registration.getEnrollee();
	}

	@Override
	protected Document getDocument(Registration registration) {
		return registration.getEnrollee().getDocument();
	}
	
}
