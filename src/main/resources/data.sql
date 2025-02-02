 
CREATE TABLE persons (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  address VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  zip VARCHAR(250) NOT NULL, 
  phone VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
  
);


CREATE TABLE firestation (
	address VARCHAR(250) NOT NULL,
	station VARCHAR (250)
);


CREATE TABLE medicalrecords (
	first_name VARCHAR(250) NOT NULL,
	last_name VARCHAR(250) NOT NULL,
	birthday VARCHAR(250) NOT NULL,
	medications VARCHAR(250), 
	allergies VARCHAR(250)
);