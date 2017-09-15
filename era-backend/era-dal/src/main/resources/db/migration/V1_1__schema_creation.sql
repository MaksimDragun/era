CREATE TABLE GENERATOR (
	GEN_NAME VARCHAR(20) NOT NULL,
	GEN_VALUE BIGINT NOT NULL DEFAULT 1000,
	PRIMARY KEY (GEN_NAME)
) ENGINE=INNODB;

CREATE TABLE EDUCATION_INSTITUTION (
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	NAME VARCHAR(128) NOT NULL,
	SHORT_NAME VARCHAR(16),
	COUNTRY CHAR(2),
	CITY VARCHAR(32),
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (EDUCATION_INSTITUTION_KEY)
) ENGINE=INNODB;

CREATE TABLE CUSTOMER (
	CUSTOMER_KEY BIGINT NOT NULL,
	CUSTOMER_NAME VARCHAR(64),
	INSTITUTION_KEY BIGINT NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	CONSTRAINT FK_CUSTOMER_INSTITUTION FOREIGN KEY (INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY),
	PRIMARY KEY (CUSTOMER_KEY)
) ENGINE=INNODB;

CREATE TABLE USER_ACCOUNT (
	USER_ACCOUNT_KEY BIGINT NOT NULL,
	USERNAME VARCHAR(20) NOT NULL,
	CUSTOMER_KEY BIGINT NOT NULL,
	FIRST_NAME VARCHAR(64),
	LAST_NAME VARCHAR(64),
	BIRTHDATE DATE,
	EMAIL VARCHAR(255) NOT NULL,
    ENABLED BIT NOT NULL,
	PASSWORD VARCHAR(80) NOT NULL,
	PASSWORD_RESET_DATE DATETIME NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (USER_ACCOUNT_KEY),
	CONSTRAINT FK_USER_ACCOUNT_CUSTOMER FOREIGN KEY (CUSTOMER_KEY) REFERENCES CUSTOMER (CUSTOMER_KEY),
    UNIQUE INDEX (USERNAME),
    UNIQUE INDEX (EMAIL)
) ENGINE=INNODB;

CREATE TABLE ROLE (
	ROLE_KEY BIGINT NOT NULL,
	MODULE VARCHAR(16) NOT NULL,
	ACTION VARCHAR(16) NOT NULL,
	PRIMARY KEY (ROLE_KEY)
) ENGINE=INNODB;

CREATE TABLE USER_ACCOUNT_ROLE (
	USER_ACCOUNT_KEY BIGINT NOT NULL,
	ROLE_KEY BIGINT NOT NULL,
	PRIMARY KEY(USER_ACCOUNT_KEY, ROLE_KEY),
	CONSTRAINT FK_ROLE_ACCOUNT FOREIGN KEY (ROLE_KEY) REFERENCES ROLE (ROLE_KEY),
    CONSTRAINT FK_ACCOUNT_ROLE FOREIGN KEY (USER_ACCOUNT_KEY) REFERENCES USER_ACCOUNT (USER_ACCOUNT_KEY)
) ENGINE=INNODB;

CREATE TABLE REGISTRATION_PERIOD (
	REGISTRATION_PERIOD_KEY BIGINT NOT NULL,
	TITLE VARCHAR(32) NOT NULL,
	FROM_DATE DATE NOT NULL,
	TO_DATE DATE NOT NULL,
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	STATUS CHAR(1) NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (REGISTRATION_PERIOD_KEY),
	CONSTRAINT FK_REG_PERIOD_EI FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY),
	CONSTRAINT CHK_REG_PERIOD_STATUS CHECK (STATUS IN ('N', 'O', 'C'))
) ENGINE=INNODB;

CREATE TABLE SPECIALTY (
	SPECIALTY_KEY BIGINT NOT NULL,
	CODE VARCHAR(16) NOT NULL,
	TITLE VARCHAR(128) NOT NULL,
	QUALIFICATION VARCHAR(32) NOT NULL,
	STATUS CHAR(1) NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (SPECIALTY_KEY),
	CONSTRAINT CHK_SPECIALTY_STATUS CHECK (STATUS IN ('A', 'I'))
) ENGINE=INNODB;

CREATE TABLE REGISTERED_SPECIALTY (
	REGISTERED_SPECIALTY_KEY BIGINT NOT NULL,
	REGISTRATION_PERIOD_KEY BIGINT NOT NULL,
	SPECIALTY_KEY BIGINT NOT NULL,
	SEPARATE_BY_BASE BIT NOT NULL,
	SEPARATE_BY_FORM BIT NOT NULL,
	SEPARATE_BY_FUNDS_SOURCE BIT NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	CONSTRAINT FK_REG_SPEC_REG_PERIOD FOREIGN KEY (REGISTRATION_PERIOD_KEY) REFERENCES REGISTRATION_PERIOD (REGISTRATION_PERIOD_KEY),
	CONSTRAINT FK_REG_SPEC_SPEC FOREIGN KEY (SPECIALTY_KEY) REFERENCES SPECIALTY (SPECIALTY_KEY),
	PRIMARY KEY (REGISTERED_SPECIALTY_KEY)
) ENGINE=INNODB;

CREATE TABLE REG_SPECIALTY_EDUCATION_BASE (
	REGISTERED_SPECIALTY_KEY BIGINT NOT NULL,
	EDUCATION_BASE CHAR(2) NOT NULL,
	PRIMARY KEY (REGISTERED_SPECIALTY_KEY, EDUCATION_BASE),
	CONSTRAINT FK_REG_SPEC_EB FOREIGN KEY (REGISTERED_SPECIALTY_KEY) REFERENCES REGISTERED_SPECIALTY (REGISTERED_SPECIALTY_KEY),
	CONSTRAINT CHK_REG_SPEC_EB CHECK (EDUCATION_BASE IN ('09', '11'))
) ENGINE=INNODB;

CREATE TABLE REG_SPECIALTY_EDUCATION_FORM (
	REGISTERED_SPECIALTY_KEY BIGINT NOT NULL,
	EDUCATION_FORM CHAR(1) NOT NULL,
	PRIMARY KEY (REGISTERED_SPECIALTY_KEY, EDUCATION_FORM),
	CONSTRAINT FK_REG_SPEC_EF FOREIGN KEY (REGISTERED_SPECIALTY_KEY) REFERENCES REGISTERED_SPECIALTY (REGISTERED_SPECIALTY_KEY),
	CONSTRAINT CHK_REG_SPEC_EF CHECK (EDUCATION_FORM IN ('F', 'E'))
) ENGINE=INNODB;

CREATE TABLE REG_SPECIALTY_FUNDS_SOURCE (
	REGISTERED_SPECIALTY_KEY BIGINT NOT NULL,
	FUNDS_SOURCE CHAR(1) NOT NULL,
	PRIMARY KEY (REGISTERED_SPECIALTY_KEY, FUNDS_SOURCE),
	CONSTRAINT FK_REG_SPEC_FS FOREIGN KEY (REGISTERED_SPECIALTY_KEY) REFERENCES REGISTERED_SPECIALTY (REGISTERED_SPECIALTY_KEY),
	CONSTRAINT CHK_REG_SPEC_FS CHECK (EDUCATION_BASE IN ('B', 'P'))
) ENGINE=INNODB;

CREATE TABLE PERSON (
	PERSON_KEY BIGINT NOT NULL,
	FIRST_NAME VARCHAR(64) NOT NULL,
	MIDDLE_NAME VARCHAR(64) NOT NULL,
	LAST_NAME VARCHAR(64) NOT NULL,
	BIRTHDATE DATE NOT NULL,
	PHONE VARCHAR(16) NOT NULL,
	EMAIL VARCHAR(256),
	
	DOCUMENT_TYPE CHAR(1) NOT NULL,
	ID VARCHAR(14) NOT NULL,
	DOCUMENT_ID VARCHAR(9) NOT NULL,
	DOCUMENT_ISSUE_DATE DATE NOT NULL,
	DOCUMENT_ISSUED_BY VARCHAR(64) NOT NULL,
	CITIZENSHIP CHAR(2) NOT NULL,
	
	COUNTRY CHAR(2) NOT NULL,
	CITY VARCHAR(32) NOT NULL,
	STREET VARCHAR(32) NOT NULL,
	HOUSE VARCHAR(8) NOT NULL,
	HOUSING VARCHAR(8),
	FLAT VARCHAR(8),
	ZIP_CODE VARCHAR(10),
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (PERSON_KEY),
	CONSTRAINT CHK_DOCUMENT_TYPE CHECK (STATUS IN ('P'))
) ENGINE=INNODB;

CREATE TABLE CERTIFICATE (
	CERTIFICATE_KEY BIGINT NOT NULL,
	INSTITUTION VARCHAR(128) NOT NULL,
	YEAR INT NOT NULL,
	ENROLLEE_KEY BIGINT NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (CERTIFICATE_KEY),
	CONSTRAINT FK_PERSON_CERTIFICATE FOREIGN KEY (ENROLLEE_KEY) REFERENCES PERSON (PERSON_KEY)
) ENGINE=INNODB;

CREATE TABLE SUBJECT (
	SUBJECT_KEY BIGINT NOT NULL,
	TITLE VARCHAR(32) NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (SUBJECT_KEY),
	UNIQUE INDEX (TITLE)
) ENGINE=INNODB;

CREATE TABLE CERTIFICATE_SUBJECT (
	SUBJECT_KEY BIGINT NOT NULL,
	CERTIFICATE_KEY BIGINT NOT NULL,
	MARK TINYINT NOT NULL,
	CONSTRAINT FK_CERTIFICATE_SUBJECT FOREIGN KEY (SUBJECT_KEY) REFERENCES SUBJECT (SUBJECT_KEY),
	CONSTRAINT FK_SUBJECT_CERTIFICATE FOREIGN KEY (CERTIFICATE_KEY) REFERENCES CERTIFICATE (CERTIFICATE_KEY)
) ENGINE=INNODB;

CREATE TABLE BENEFIT (
	BENEFIT_KEY BIGINT NOT NULL,
	TYPE CHAR(1) NOT NULL,
	PRIORITY INT NOT NULL,
	DELETED BIT NOT NULL DEFAULT 0,
	NAME VARCHAR(64) NOT NULL,
	DESCRIPTION VARCHAR(1024),
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (BENEFIT_KEY),
	CONSTRAINT CHK_BENEFIT_TYPE CHECK (TYPE IN ('P', 'O'))
) ENGINE=INNODB;

CREATE TABLE REGISTRATION (
	REGISTRATION_KEY BIGINT NOT NULL,
	REGISTRATION_ID BIGINT NOT NULL,
	FUNDS_SOURCE CHAR(1) NOT NULL,
	EDUCATION_FORM CHAR(1) NOT NULL,
	EDUCATION_BASE CHAR(2) NOT NULL,
	STATUS CHAR(1) NOT NULL,
	NOTE VARCHAR(32),
	REGISTRATION_PERIOD_KEY BIGINT NOT NULL,
	REGISTRATION_DATE DATE NOT NULL,
	ENROLLEE_KEY BIGINT NOT NULL,
	REGISTERED_BY BIGINT NOT NULL,
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	SPECIALTY_KEY BIGINT NOT NULL,
	CERTIFICATE_KEY BIGINT,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (REGISTRATION_KEY),
	CONSTRAINT FK_REGISTRATION_EN FOREIGN KEY (ENROLLEE_KEY) REFERENCES PERSON (PERSON_KEY),
	CONSTRAINT FK_REGISTRATION_UA FOREIGN KEY (REGISTERED_BY) REFERENCES USER_ACCOUNT (USER_ACCOUNT_KEY),
	CONSTRAINT FK_REGISTRATION_EI FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY),
	CONSTRAINT FK_REGISTRATION_SP FOREIGN KEY (SPECIALTY_KEY) REFERENCES SPECIALTY (SPECIALTY_KEY),
	CONSTRAINT FK_REGISTRATION_CE FOREIGN KEY (CERTIFICATE_KEY) REFERENCES CERTIFICATE (CERTIFICATE_KEY),
	CONSTRAINT FK_REGISTRATION_RP FOREIGN KEY (REGISTRATION_PERIOD_KEY) REFERENCES REGISTRATION_PERIOD (REGISTRATION_PERIOD_KEY),
	CONSTRAINT CHK_REGISTRATION_FUNDS_SOURCE CHECK (FUNDS_SOURCE IN ('B', 'P')),
	CONSTRAINT CHK_REGISTRATION_FORM CHECK (EDUCATION_FORM IN ('F', 'E')),
	CONSTRAINT CHK_REGISTRATION_BASE CHECK (EDUCATION_BASE IN ('09', '11')),
	CONSTRAINT CHK_REGISTRATION_STATUS CHECK (EDUCATION_BASE IN ('P', 'C', 'A', 'W'))
) ENGINE=INNODB;

CREATE TABLE REGISTRATION_BENEFIT (
	REGISTRATION_KEY BIGINT NOT NULL,
	BENEFIT_KEY BIGINT NOT NULL,
	PRIMARY KEY (REGISTRATION_KEY, BENEFIT_KEY),
	CONSTRAINT FK_REG_REG_BNF FOREIGN KEY (REGISTRATION_KEY) REFERENCES REGISTRATION (REGISTRATION_KEY),
	CONSTRAINT FK_BNF_REG_BNF FOREIGN KEY (BENEFIT_KEY) REFERENCES BENEFIT (BENEFIT_KEY)
) ENGINE=INNODB;

CREATE TABLE REPORT_TEMPLATE (
	REPORT_TEMPLATE_KEY BIGINT NOT NULL,
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	FILE_EXTENSION VARCHAR(4) NOT NULL,
	TITLE VARCHAR(80) NOT NULL,
	TEMPLATE LONGBLOB NOT NULL,
	VERSION BIGINT NOT NULL DEFAULT 0,
	PRIMARY KEY (REPORT_TEMPLATE_KEY),
	CONSTRAINT FK_TEMPLATE_EI FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY)
) ENGINE=INNODB;
