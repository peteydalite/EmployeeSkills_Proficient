BEGIN TRANSACTION;

DROP TABLE if EXISTS employee_to_skills;
DROP TABLE if EXISTS skills;
DROP TABLE if EXISTS fields;
DROP TABLE if EXISTS employee;
DROP TABLE if EXISTS address;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE address(
    address_id uuid NOT NULL,
    street varchar(70) NOT NULL,
    suite varchar(70),
    city char(70) NOT NULL,
    region char(3) NOT NULL,
    postal int NOT NULL,
    country char(100) NOT NULL,
    CONSTRAINT PK_address PRIMARY KEY (address_id)
);

CREATE TABLE employee(
    employee_id uuid NOT NULL,
    firstname varchar(100) NOT NULL,
    lastname varchar(100) NOT NULL,
    address_id uuid NOT NULL,
    contact_email varchar(100),
    company_email varchar(100) NOT NULL,
    birthdate date NOT NULL,
    hire_date date NOT NULL,
    role char(100) DEFAULT  'Technical Consultant',
    business_unit varchar(100),
    assigned_to uuid,
    CONSTRAINT PK_employee PRIMARY KEY (employee_id),
    CONSTRAINT FK_address FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE fields(
    field_id uuid NOT NULL,
    name varchar(100) NOT NULL,
    type varchar(100) NOT NULL,
    CONSTRAINT PK_field PRIMARY KEY (field_id)
);

CREATE TABLE skills(
    skill_id uuid NOT NULL,
    experience int DEFAULT 0,
    summary varchar(500),
    field_id uuid NOT NULL,
    CONSTRAINT PK_skill PRIMARY KEY (skill_id),
    CONSTRAINT FK_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

CREATE TABLE employee_to_skills(
    employee_id uuid NOT NULL,
    skill_id uuid NOT NULL,
    CONSTRAINT FK_employee FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    CONSTRAINT FK_skill FOREIGN KEY (skill_id) REFERENCES skills(skill_id)
);

INSERT INTO Address VALUES ('bb4d5a82-dee3-408e-97fe-4fd9e1a87982', '123 Fake St', 'Unit 999', 'Test', 'MD', 67831, 'United States');
INSERT INTO Employee (employee_id, firstname, lastname, address_id, contact_email, company_email, birthdate, hire_date, role, business_unit)  VALUES('d767d568-0bf8-11eb-adc1-0242ac120002', 'TestF', 'TestL', 'bb4d5a82-dee3-408e-97fe-4fd9e1a87982', null, 'test@company.com', '1999-10-10', '2020-01-05', 'Technical Consultant', 'IBM');
INSERT INTO Fields VALUES ('0cf253e7-930d-4478-b6de-942034801655', 'Test Field','TEST');
INSERT INTO Fields VALUES ('2d0b1e71-5268-42e1-964c-fcdce6e0b62e', 'Machine Learning', 'Research and Development');
INSERT INTO Skills Values ('d2923b32-7b04-400e-a217-947cb80f0e1c', 2, 'Continuous Education', '2d0b1e71-5268-42e1-964c-fcdce6e0b62e');
INSERT INTO employee_to_skills VALUES( 'd767d568-0bf8-11eb-adc1-0242ac120002', 'd2923b32-7b04-400e-a217-947cb80f0e1c');

COMMIT TRANSACTION;