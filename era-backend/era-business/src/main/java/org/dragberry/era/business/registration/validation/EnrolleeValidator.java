package org.dragberry.era.business.registration.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.Validator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.Registration;

public class EnrolleeValidator implements Validator<Registration> {

	private interface Errors extends RegistrationCommon {
		String ENROLLEE_DETAILS_IS_EMPTY = RegistrationCommon.errorCode("enrollee.enrollee-details-is-empty");
		String ENROLLEE_FIRST_NAME_IS_EMPTY = RegistrationCommon.errorCode("enrollee.enrollee-first-name-is-empty");
		String ENROLLEE_FIRST_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.enrollee-first-name-is-too-long");
		String ENROLLEE_LAST_NAME_IS_EMPTY = RegistrationCommon.errorCode("enrollee.enrollee-last-name-is-empty");
		String ENROLLEE_LAST_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.enrollee-last-name-is-too-long");
		String ENROLLEE_MIDDLE_NAME_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.enrollee-middle-name-is-too-long");
		String ENROLLEE_BIRTHDATE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.enrollee-birthdate-is-empty");
		String ENROLLEE_BIRTHDAY_IS_TOO_YOUNG = RegistrationCommon.errorCode("enrollee.enrollee-birthday-is-too-young");
		
		String ENROLLEE_DOCUMENT_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document-details-is-empty");
		String ENROLLEE_DOCUMENT_TYPE_IS_INVALID = RegistrationCommon.errorCode("enrollee.document-type-is-invalid");
		String ENROLLEE_ID_IS_EMPTY = RegistrationCommon.errorCode("enrollee.id-is-empty");
		String ENROLLEE_ID_HAS_INVALID_LENGTH = RegistrationCommon.errorCode("enrollee.id-has-invalid-length");
		String ENROLLEE_DOCUMENT_ID_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document-id-is-empty");
		String ENROLLEE_DOCUMENT_ID_HAS_INVALID_LENGTH = RegistrationCommon.errorCode("enrollee.document-id-has-invalid-length");
		String ENROLLEE_DOCUMENT_ISSUE_DATE_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document-issue-date-is-empty");
		String ENROLLEE_DOCUMENT_ISSUE_DATE_IS_IN_FUTURE = RegistrationCommon.errorCode("enrollee.document-issue-date-is-in-future");
		String ENROLLEE_DOCUMENT_ISSUED_BY_IS_EMPTY = RegistrationCommon.errorCode("enrollee.document-issued-by-is-empty");
		String ENROLLEE_DOCUMENT_ISSUED_BY_IS_TOO_LONG = RegistrationCommon.errorCode("enrollee.document-issued-by-is-too-long");
	}
	
	private interface FieldID {
		String ENROLEE_FIRST_NAME = "eFirstName";
		String ENROLEE_LAST_NAME = "eLastName";
		String ENROLEE_MIDDLE_NAME = "eMiddleName";
		String ENROLEE_BIRTHDATE = "eBirthdate";
		
		String DOCUMENT_TYPE = "dType";
		String DOCUMENT_ID = "dId";
		String DOCUMENT_DOCUMENT_ID = "dDocumentId";
		String DOCUMENT_ISSUE_DATE = "dIssueDate";
		String DOCUMENT_ISSUED_BY = "dIssuedBy";
	}
	
	@Override
	public List<IssueTO> validate(Registration entity) {
		Person enrollee = entity.getEnrollee();
		if (enrollee == null) {
			return issues(Issues.create(Errors.ENROLLEE_DETAILS_IS_EMPTY));
		}
		// First name
		List<IssueTO> issues = new ArrayList<>();
		if (StringUtils.isBlank(enrollee.getFirstName())) {
			issues.add(Issues.create(Errors.ENROLLEE_FIRST_NAME_IS_EMPTY, FieldID.ENROLEE_FIRST_NAME));
		} else {
			issues.addAll(validateFieldMaxLength(enrollee.getFirstName(), 32, Errors.ENROLLEE_FIRST_NAME_IS_TOO_LONG, FieldID.ENROLEE_FIRST_NAME));
		}
		// Last name
		if (StringUtils.isBlank(enrollee.getLastName())) {
			issues.add(Issues.create(Errors.ENROLLEE_LAST_NAME_IS_EMPTY, FieldID.ENROLEE_LAST_NAME));
		} else {
			issues.addAll(validateFieldMaxLength(enrollee.getLastName(), 32, Errors.ENROLLEE_LAST_NAME_IS_TOO_LONG, FieldID.ENROLEE_LAST_NAME));
		}
		// Middle name
		issues.addAll(validateFieldMaxLength(enrollee.getMiddleName(), 32, Errors.ENROLLEE_MIDDLE_NAME_IS_TOO_LONG, FieldID.ENROLEE_MIDDLE_NAME));
		// Birthdate
		if (enrollee.getBirthdate() == null) {
			issues.add(Issues.create(Errors.ENROLLEE_BIRTHDATE_IS_EMPTY, FieldID.ENROLEE_BIRTHDATE));
		} else {
			if (enrollee.getBirthdate().isAfter(LocalDate.now().minusYears(15))) {
				issues.add(Issues.create(Errors.ENROLLEE_BIRTHDAY_IS_TOO_YOUNG, FieldID.ENROLEE_BIRTHDATE));
			}
		}
		
		// Document
		Document document = enrollee.getDocument();
		if (document == null) {
			issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_IS_EMPTY));
		} else {
			// Document type
			if (document.getType() == null) {
				issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_TYPE_IS_INVALID, FieldID.DOCUMENT_TYPE));
			}
			// Id
			if (StringUtils.isBlank(document.getId())) {
				issues.add(Issues.create(Errors.ENROLLEE_ID_IS_EMPTY, FieldID.DOCUMENT_ID));
			} else {
				issues.addAll(validateFieldLength(document.getId(), 14, Errors.ENROLLEE_ID_HAS_INVALID_LENGTH, FieldID.DOCUMENT_ID));
			}
			// Document id
			if (StringUtils.isBlank(document.getDocumentId())) {
				issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_ID_IS_EMPTY, FieldID.DOCUMENT_DOCUMENT_ID));
			} else {
				issues.addAll(validateFieldLength(document.getDocumentId(), 9, Errors.ENROLLEE_DOCUMENT_ID_HAS_INVALID_LENGTH, FieldID.DOCUMENT_DOCUMENT_ID));
			}
			// Document issue date
			if (document.getIssueDate() == null) {
				issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_ISSUE_DATE_IS_EMPTY, FieldID.DOCUMENT_ISSUE_DATE));
			} else {
				if (document.getIssueDate().isAfter(LocalDate.now())) {
					issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_ISSUE_DATE_IS_IN_FUTURE, FieldID.DOCUMENT_ISSUE_DATE));
				}
			}
			// Document issued by
			if (StringUtils.isBlank(document.getIssuedBy())) {
				issues.add(Issues.create(Errors.ENROLLEE_DOCUMENT_ISSUED_BY_IS_EMPTY, FieldID.DOCUMENT_ISSUED_BY));
			} else {
				issues.addAll(validateFieldMaxLength(document.getIssuedBy(), 32, Errors.ENROLLEE_DOCUMENT_ISSUED_BY_IS_TOO_LONG, FieldID.DOCUMENT_ISSUED_BY));
			}
		}
		
		return issues;
	}

}
