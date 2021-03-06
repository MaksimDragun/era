package org.dragberry.era.web.controller;

import org.dragberry.era.business.useraccount.UserAccountService;
import org.dragberry.era.common.Results;
import org.dragberry.era.common.useraccount.RoleHolderTO;
import org.dragberry.era.common.useraccount.UserAccountCRUDTO;
import org.dragberry.era.security.AccessControl;
import org.dragberry.era.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-account")
public class UserAccountController {
	
	@Autowired
	private AccessControl accessContoll;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@GetMapping("/get-list")
	public ResponseEntity<?> fetchList() {
		accessContoll.checkPermission(Roles.UserAccounts.VIEW);
		return ResponseEntity.ok(Results.create(userAccountService.getListForCustomer(accessContoll.getLoggedUser().getCustomerId())));
	}
	
	@GetMapping("/get-details/{userAccountId}")
	public ResponseEntity<?> fetchList(@PathVariable Long userAccountId) {
		accessContoll.checkPermission(Roles.UserAccounts.VIEW);
		UserAccountCRUDTO request = new UserAccountCRUDTO();
		request.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		request.setUserAccountId(accessContoll.getLoggedUser().getId());
		request.setId(userAccountId);
		return ResponseEntity.ok(userAccountService.getDetails(request));
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUserAccount(@RequestBody UserAccountCRUDTO userAccount) {
		accessContoll.checkPermission(Roles.UserAccounts.CREATE);
		userAccount.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		userAccount.getRoles().stream().filter(RoleHolderTO::getEnabled).map(RoleHolderTO::getRole).forEach(accessContoll::checkPermission);
		return ResponseEntity.ok(userAccountService.create(userAccount));
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateUserAccount(@RequestBody UserAccountCRUDTO userAccount) {
		accessContoll.checkPermission(Roles.UserAccounts.UPDATE);
		userAccount.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		userAccount.getRoles().stream().filter(RoleHolderTO::getEnabled).map(RoleHolderTO::getRole).forEach(accessContoll::checkPermission);
		return ResponseEntity.ok(userAccountService.update(userAccount));
	}
	
	@DeleteMapping("/delete/{userAccountId}")
	public ResponseEntity<?> deleteUserAccount(@PathVariable Long userAccountId) {
		accessContoll.checkPermission(Roles.UserAccounts.DELETE);
		UserAccountCRUDTO request = new UserAccountCRUDTO();
		request.setCustomerId(accessContoll.getLoggedUser().getCustomerId());
		request.setUserAccountId(accessContoll.getLoggedUser().getId());
		request.setId(userAccountId);
		return ResponseEntity.ok(userAccountService.delete(request));
	}

}
