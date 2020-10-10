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
    suit varchar(70),
    city char(70) NOT NULL,
    region char(70) NOT NULL,
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
    assigned_to uuid NOT NULL,
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
    skills_id uuid NOT NULL,
    experience int DEFAULT 0,
    summary varchar(500),
    field_id uuid NOT NULL,
    CONSTRAINT PK_skills PRIMARY KEY (skills_id),
    CONSTRAINT FK_field FOREIGN KEY (field_id) REFERENCES fields(field_id)
);

CREATE TABLE employee_to_skills(
    employee_id uuid NOT NULL,
    skills_id uuid NOT NULL,
    CONSTRAINT FK_employee FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
    CONSTRAINT FK_skills FOREIGN KEY (skills_id) REFERENCES skills(skills_id)
);
COMMIT TRANSACTION;