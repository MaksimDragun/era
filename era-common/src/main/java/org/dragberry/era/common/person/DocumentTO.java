package org.dragberry.era.common.person;

import java.io.Serializable;
import java.time.LocalDate;

public class DocumentTO implements Serializable {

	private static final long serialVersionUID = 8365638606145665802L;

	private Character type;
	
	private String id;
	
	private String documnetId;
	
	private LocalDate issueDate;
	
	private String issuedBy;

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumnetId() {
		return documnetId;
	}

	public void setDocumnetId(String documnetId) {
		this.documnetId = documnetId;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	
}