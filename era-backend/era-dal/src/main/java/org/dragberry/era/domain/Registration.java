package org.dragberry.era.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.EducationBaseConverter;
import org.dragberry.era.domain.converter.EducationFormConverter;
import org.dragberry.era.domain.converter.FundsSourceConverter;
import org.dragberry.era.domain.converter.RegistrationStatusConverter;

@Entity
@Table(name = "REGISTRATION")
@TableGenerator(
		name = "REGISTRATION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REGISTRATION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Registration extends BaseEntity {

	private static final long serialVersionUID = 8173371976074070183L;

	@Id
	@Column(name = "REGISTRATION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REGISTRATION_GEN")
	private Long entityKey;
	
	@Column(name = "REGISTRATION_ID")
	private Long registrationId;
	
	@Column(name = "FUNDS_SOURCE")
	@Convert(converter = FundsSourceConverter.class)
	private FundsSource fundsSource;
	
	@Column(name = "EDUCATION_FORM")
	@Convert(converter = EducationFormConverter.class)
	private EducationForm educationForm;
	
	@Column(name = "EDUCATION_BASE")
	@Convert(converter = EducationBaseConverter.class)
	private EducationBase educationBase;
	
	@Column(name = "NOTE")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "PERSON_KEY")
	private Person enrollee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYER_KEY", referencedColumnName = "PERSON_KEY")
	private Person payer;
	
	@Column(name = "ENROLLEE_AS_PAYER")
	private Boolean enrolleeAsPayer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_PERIOD_KEY", referencedColumnName = "REGISTRATION_PERIOD_KEY")
	private RegistrationPeriod registrationPeriod;
	
	@Column(name = "REGISTRATION_DATE")
	private LocalDateTime registrationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTERED_BY", referencedColumnName = "USER_ACCOUNT_KEY")
	private UserAccount registeredBy;
	
	@Column(name = "VERIFICATION_DATE")
	private LocalDateTime verificationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VERIFIED_BY", referencedColumnName = "USER_ACCOUNT_KEY")
	private UserAccount verifiedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDUCATION_INSTITUTION_KEY", referencedColumnName = "EDUCATION_INSTITUTION_KEY")
	private EducationInstitution institution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALTY_KEY", referencedColumnName = "SPECIALTY_KEY")
	private Specialty specialty;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATE_KEY", referencedColumnName = "CERTIFICATE_KEY")
	private Certificate certificate;
	
	@Column(name = "STATUS")
	@Convert(converter = RegistrationStatusConverter.class)
	private Status status;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REGISTRATION_PREROGATIVE", 
        joinColumns = @JoinColumn(name = "REGISTRATION_KEY", referencedColumnName = "REGISTRATION_KEY"), 
        inverseJoinColumns = @JoinColumn(name = "PREROGATIVE_KEY", referencedColumnName = "PREROGATIVE_KEY"))
	private List<Prerogative> prerogatives;
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REGISTRATION_OUT_OF_COMPETITION", 
        joinColumns = @JoinColumn(name = "REGISTRATION_KEY", referencedColumnName = "REGISTRATION_KEY"), 
        inverseJoinColumns = @JoinColumn(name = "OUT_OF_COMPETITION_KEY", referencedColumnName = "OUT_OF_COMPETITION_KEY"))
	private List<OutOfCompetition> outOfCompetitions;
	
	@ElementCollection
	@CollectionTable(name = "REGISTRATION_EXAM_SUBJECT", joinColumns = @JoinColumn(name = "REGISTRATION_KEY"))
	@MapKeyJoinColumn(name = "EXAM_SUBJECT_KEY")
	@Column(name = "MARK")
	private Map<ExamSubject, Integer> examMarks;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public UserAccount getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(UserAccount registeredBy) {
		this.registeredBy = registeredBy;
	}

	public EducationInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(EducationInstitution institution) {
		this.institution = institution;
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty speciality) {
		this.specialty = speciality;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Person getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Person enrollee) {
		this.enrollee = enrollee;
	}
	
	public FundsSource getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(FundsSource fundsSource) {
		this.fundsSource = fundsSource;
	}

	public EducationForm getEducationForm() {
		return educationForm;
	}

	public void setEducationForm(EducationForm educationForm) {
		this.educationForm = educationForm;
	}

	public RegistrationPeriod getRegistrationPeriod() {
		return registrationPeriod;
	}

	public void setRegistrationPeriod(RegistrationPeriod registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public EducationBase getEducationBase() {
		return educationBase;
	}

	public void setEducationBase(EducationBase educationBase) {
		this.educationBase = educationBase;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Prerogative> getPrerogatives() {
		return prerogatives;
	}

	public void setPrerogatives(List<Prerogative> prerogatives) {
		this.prerogatives = prerogatives;
	}

	public List<OutOfCompetition> getOutOfCompetitions() {
		return outOfCompetitions;
	}

	public void setOutOfCompetitions(List<OutOfCompetition> outOfCompetitions) {
		this.outOfCompetitions = outOfCompetitions;
	}
	

	public LocalDateTime getVerificationDate() {
		return verificationDate;
	}

	public void setVerificationDate(LocalDateTime verificationDate) {
		this.verificationDate = verificationDate;
	}

	public UserAccount getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(UserAccount verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	
	public Person getPayer() {
		return payer;
	}

	public void setPayer(Person payer) {
		this.payer = payer;
	}

	public Map<ExamSubject, Integer> getExamMarks() {
		return examMarks;
	}

	public void setExamMarks(Map<ExamSubject, Integer> examMarks) {
		this.examMarks = examMarks;
	}

	public Boolean isEnrolleeAsPayer() {
		return enrolleeAsPayer;
	}

	public void setEnrolleeAsPayer(Boolean enrolleeAsPayer) {
		this.enrolleeAsPayer = enrolleeAsPayer;
	}

	public static enum Status implements BaseEnum<Character> {
		UNCOMPLETE('U'), NOT_VERIFIED('N'), VERIFIED('V'), ACCEPTED('A'), CANCELED('C');
		
		public final Character value;
		
		private Status(Character value) {
			this.value = value;
		}
		
		public static Status resolve(Character value) {
			if (value == null) {
				throw BaseEnum.npeException(Status.class);
			}
			for (Status src : Status.values()) {
				if (value.equals(src.value)) {
					return src;
				}
			}
			throw BaseEnum.unknownValueException(Status.class, value);
		}
		
		@Override
		public Character getValue() {
			return value;
		}
	}
}
