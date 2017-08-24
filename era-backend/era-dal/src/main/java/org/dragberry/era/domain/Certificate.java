package org.dragberry.era.domain;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "CERTIFICATE")
@TableGenerator(
		name = "CERTIFICATE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "CERTIFICATE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Certificate extends AbstractEntity {
	
	private static final long serialVersionUID = 8040613837195453060L;
	
	@Id
	@Column(name = "CERTIFICATE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CERTIFICATE_GEN")
	private Long entityKey;
	
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "INSTITUTION")
	private String institution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "ENROLLEE_KEY")
	private Enrollee enrollee;
	
	@ElementCollection
	@CollectionTable(name = "CERTIFICATE_SUBJECT", joinColumns = @JoinColumn(name = "CERTIFICATE_KEY"))
	@MapKeyJoinColumn(name = "SUBJECT_KEY")
	@Column(name = "MARK")
	private Map<Subject, Integer> marks;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Map<Subject, Integer> getMarks() {
		return marks;
	}

	public void setMarks(Map<Subject, Integer> marks) {
		this.marks = marks;
	}

	public Enrollee getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Enrollee enrollee) {
		this.enrollee = enrollee;
	}
	
}
