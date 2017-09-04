package org.dragberry.era.business.useraccount;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.common.ResultTO;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.useraccount.UserAccountCreateTO;
import org.dragberry.era.common.useraccount.UserAccountTO;
import org.dragberry.era.dao.CustomerDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.AbstractEntity;
import org.dragberry.era.domain.Role;
import org.dragberry.era.domain.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAccountServiceBean implements UserAccountService {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<UserAccountTO> getListForCustomer(Long customerKey) {
		return userAccountDao.findAccountsForCustomer(customerKey).stream().map(entity -> {
			UserAccountTO to = new UserAccountTO();
			to.setUsername(entity.getUsername());
			to.setId(entity.getEntityKey());
			to.setFirstName(entity.getFirstName());
			to.setLastName(entity.getLastName());
			to.setEmail(entity.getEmail());
			to.setRoles(entity.getRoles().stream().map(Role::toString).collect(Collectors.toList()));
			return to;
		}).collect(Collectors.toList());
	}
	
	@Override
	public ResultTO<UserAccountCreateTO> create(UserAccountCreateTO userAccount) {
		List<IssueTO> issues = new ArrayList<>();
		// Username
		if (StringUtils.isBlank(userAccount.getUsername())) {
			issues.add(Issues.create("validation.user-account.username-is-empty"));
		} else {
			if (userAccount.getUsername().length() < 3) {
				issues.add(Issues.create("validation.user-account.username-is-small"));
			} else if (userAccount.getUsername().length() > 20) {
				issues.add(Issues.create("validation.user-account.username-is-large"));
			} else {
				if (userAccountDao.findByUsername(userAccount.getUsername()) != null) {
					issues.add(Issues.create("validation.user-account.username-exists"));
				}
			}
		}
		// E-mail
		if (StringUtils.isBlank(userAccount.getEmail())) {
			issues.add(Issues.create("validation.user-account.email-is-empty"));
		} else {
			if (!VALID_EMAIL_ADDRESS_REGEX .matcher(userAccount.getEmail()).matches()) {
				issues.add(Issues.create("validation.user-account.email-is-invalid"));
			} else {
				if (userAccountDao.findByEmail(userAccount.getEmail()) != null) {
					issues.add(Issues.create("validation.user-account.email-exists"));
				}
			}
		}
		// First name
		if (StringUtils.isBlank(userAccount.getFirstName())) {
			issues.add(Issues.create("validation.user-account.first-name-is-empty"));
		} else {
			if (userAccount.getFirstName().length() > 64) {
				issues.add(Issues.create("validation.user-account.first-name-is-large"));
			}
		}
		// Last name
		if (StringUtils.isBlank(userAccount.getLastName())) {
			issues.add(Issues.create("validation.user-account.last-name-is-empty"));
		} else {
			if (userAccount.getLastName().length() > 64) {
				issues.add(Issues.create("validation.user-account.last-name-is-large"));
			}
		}
		// Password
		if (StringUtils.isBlank(userAccount.getPassword()) || StringUtils.isBlank(userAccount.getRepeatedPassword())) {
			issues.add(Issues.create("validation.user-account.password-is-empty"));
		} else {
			if (!Objects.equals(userAccount.getPassword(), userAccount.getRepeatedPassword())) {
				issues.add(Issues.create("validation.user-account.passwords-dont-match"));
			} else {
				if (userAccount.getPassword().length() < 8) {
					issues.add(Issues.create("validation.user-account.password-is-small"));
				} else if (userAccount.getPassword().length() > 20) {
					issues.add(Issues.create("validation.user-account.password-is-large"));
				}
			}
		}
		// Bidthdate
		
		LocalDate birthdate = null;
		if (StringUtils.isNotBlank(userAccount.getBirthdate())) {
			try {
				birthdate =  LocalDate.parse(userAccount.getBirthdate());
			} catch(DateTimeParseException ex) {
				issues.add(Issues.create("validation.user-account.birthdate-invalid"));
			}
		}
		
		if (issues.isEmpty()) {
			UserAccount account = new UserAccount();
			account.setUsername(userAccount.getUsername());
			account.setEmail(userAccount.getEmail());
			account.setFirstName(userAccount.getFirstName());
			account.setLastName(userAccount.getLastName());
			account.setBirthdate(birthdate);
			account.setPassword(passwordEncoder.encode(userAccount.getPassword()));
			account.setEnabled(true);
			account.setLastPasswordResetDate(LocalDateTime.now());
			account.setRoles(null);
			account.setCustomer(customerDao.findOne(userAccount.getCustomerId()));
			
			account = userAccountDao.create(account);
			userAccount.setId(account.getEntityKey());
		}
		return Results.create(userAccount, issues);
	}

	interface EntityBuilder<T extends Serializable, E extends AbstractEntity> {
		
		ResultTO<E> build(T object);
		
		
	}
}