INSERT INTO GENERATOR (GEN_NAME) VALUES ('BENEFIT_GEN');

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE,  DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		0, 'P', 0, 'Инвалиды I и II групп', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		1, 'P', 0, 'Дети военнослужащих (умерших или инвалидов)', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		2, 'P', 0, 'Суворовцы', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		3, 'P', 0, 'ЧАЭС (ст. 18)', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		4, 'P', 0, 'Инвалиды III группы', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';

INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		5, 'P', 0, 'ЧАЭС (ст. 19-23)', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';


INSERT INTO BENEFIT 
	(BENEFIT_KEY, PRIORITY, TYPE, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'BENEFIT_GEN'),
		6, 'P', 0, 'Многотетная семья', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'BENEFIT_GEN';
