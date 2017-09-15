package org.dragberry.era.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.EnumSet;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.dao.EducationInstitutionDao;
import org.dragberry.era.dao.PersonDao;
import org.dragberry.era.dao.RegistrationDao;
import org.dragberry.era.dao.RegistrationPeriodDao;
import org.dragberry.era.dao.SpecialtyDao;
import org.dragberry.era.dao.SubjectDao;
import org.dragberry.era.dao.UserAccountDao;
import org.dragberry.era.domain.Address;
import org.dragberry.era.domain.Certificate;
import org.dragberry.era.domain.Document;
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.EducationForm;
import org.dragberry.era.domain.EducationInstitution;
import org.dragberry.era.domain.FundsSource;
import org.dragberry.era.domain.Person;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.Registration;
import org.dragberry.era.domain.RegistrationPeriod;
import org.dragberry.era.domain.Subject;
import org.dragberry.era.domain.UserAccount;
import org.dragberry.era.domain.Document.Type;
import org.dragberry.era.domain.RegistrationPeriod.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DummyDataBean {
	
	@Autowired
	private PersonDao personDao;
	@Autowired
	private CertificateDao certificateDao;
	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private SpecialtyDao specialityDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private EducationInstitutionDao einstitutionDao;
	@Autowired
	private RegistrationPeriodDao registrationPeriodDao;
	@Autowired
	private RegistrationDao registrationDao;
	
	private UserAccount registeredBy;
	private EducationInstitution eInstitution;
	private RegistrationPeriod period;
	
	@Transactional
	public void createTestData() {
		if (registrationPeriodDao.findOne(1000L) != null) {
			return;
		}
		registeredBy = userAccountDao.findOne(1000L);
		eInstitution = einstitutionDao.findOne(1000l);
		
		period = period(eInstitution);
		registrationPeriodDao.create(period);
		
		// All separated
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(1, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(2, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(3, 0, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
		//separated by form and base
		createRegistration(1, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(1, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(2, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(3, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(4, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(5, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(6, 1, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
		//separated by nothing
		createRegistration(1, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(2, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(3, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(4, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(5, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(6, 5, EducationForm.FULL_TIME, EducationBase.L9, FundsSource.PAYER);
		createRegistration(7, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(8, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(9, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(10, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(11, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(12, 5, EducationForm.FULL_TIME, EducationBase.L11, FundsSource.PAYER);
		createRegistration(13, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(14, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(15, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.BUDGET);
		createRegistration(16, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(17, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(18, 5, EducationForm.EXTRAMURAL, EducationBase.L9, FundsSource.PAYER);
		createRegistration(19, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(20, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(21, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.BUDGET);
		createRegistration(22, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(23, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		createRegistration(24, 5, EducationForm.EXTRAMURAL, EducationBase.L11, FundsSource.PAYER);
		
	}
	
	private void createRegistration(long id, int specId, EducationForm form, EducationBase base, FundsSource source) {
		Person person = person();
		personDao.create(person);
		Certificate certificate = certificate(person);
		certificateDao.create(certificate);
		Registration registration = registration(id, specId, form, base, source, person, certificate);
		registrationDao.create(registration);
	}

	private Registration registration(long id, int specId, EducationForm form, EducationBase base, FundsSource source,
			Person enr, Certificate certificate) {
		Registration registration = new Registration();
		registration.setCertificate(certificate);
		registration.setEnrollee(enr);
		registration.setInstitution(eInstitution);
		registration.setRegisteredBy(registeredBy);
		registration.setRegistrationDate(LocalDateTime.now());
		registration.setSpecialty(period.getSpecialties().get(specId).getSpecialty());
		registration.setRegistrationId(id);
		registration.setRegistrationPeriod(period);
		registration.setFundsSource(source);
		registration.setEducationForm(form);
		registration.setEducationBase(base);
		return registration;
	}
	
	private RegistrationPeriod period(EducationInstitution eInstitution) {
		RegistrationPeriod period = new RegistrationPeriod();
		period.setEducationInstitution(eInstitution);
		period.setFrom(LocalDate.of(2017, Month.JUNE, 1));
		period.setTo(LocalDate.of(2017, Month.DECEMBER, 1));
		period.setTitle("2017/2018 учебный год");
		period.setStatus(Status.OPENED);

		period.setSpecialties(specialityDao.fetchList().stream().map(spec -> {
			RegisteredSpecialty rSpec = new RegisteredSpecialty();
			rSpec.setSpecialty(spec);
			rSpec.setRegistrationPeriod(period);
			switch (spec.getEntityKey().intValue()) {
				case 1000:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
				case 1001:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
				case 1002:
					rSpec.setSeparateByEducationBase(true);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(false);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
				case 1003:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(true);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
				case 1004:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(true);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
				case 1005:
					rSpec.setSeparateByEducationBase(false);
					rSpec.setEducationBases(EnumSet.allOf(EducationBase.class));
					rSpec.setSeparateByEducationForm(false);
					rSpec.setEducationForms(EnumSet.allOf(EducationForm.class));
					rSpec.setSeparateByFundsSource(false);
					rSpec.setFundsSources(EnumSet.allOf(FundsSource.class));
					break;
			}
			return rSpec;
		}).collect(Collectors.toList()));
		return period;
	}
	
	private Person person() {
		Person enr = new Person();
		enr.setFirstName(RandomProvider.getName());
		enr.setMiddleName(RandomProvider.getMiddleName());
		enr.setLastName(RandomProvider.getSurname());
		enr.setBirthdate(RandomProvider.getBirthDate());
		enr.setPhone(RandomProvider.getPhone());
		enr.setEmail(RandomProvider.getMail());
		
		Document doc = new Document();
		doc.setType(Type.PASSPORT);
		doc.setIssueDate(RandomProvider.getDate());
		doc.setIssuedBy(RandomProvider.getRUVD());
		doc.setId(RandomProvider.getString(14));
		doc.setDocumentId(RandomProvider.getString("MP", 7));
		enr.setDocument(doc);
		
		Address addr = new Address();
		addr.setCity(RandomProvider.getCity());
		addr.setCountry("BY");
		addr.setFlat(RandomProvider.getNumber());
		addr.setHouse(RandomProvider.getNumber());
		addr.setStreet(RandomProvider.getStreet());
		addr.setZipCode(RandomProvider.getString(6));
		enr.setAddress(addr);
		return enr;
	}
	
	private Certificate certificate(Person enrollee) {
		Certificate certificate = new Certificate();
		certificate.setInstitution(RandomProvider.getSchool());
		certificate.setYear(RandomProvider.getYear());
		certificate.setEnrollee(enrollee);
		
		Map<Subject, Integer> marks;
		Random random = new Random();
		marks = subjectDao.fetchList().stream().collect(Collectors.toMap(subject -> subject, subject -> Math.abs(random.nextInt() % 11)));
		certificate.setMarks(marks);
		return certificate;
	}

}