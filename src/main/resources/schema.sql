create table USER (
	id INTEGER PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR(50),
	phone VARCHAR(15),
	password VARCHAR(60),
	roles VARCHAR(50),
	enabled BOOLEAN,
	non_expired BOOLEAN,
	non_locked BOOLEAN,
	CONSTRAINT USER_email_UNIQUE UNIQUE (email)
);