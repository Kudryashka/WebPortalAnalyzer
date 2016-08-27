CREATE TABLE users_infos (
	id SERIAL PRIMARY KEY,
	request_date TIMESTAMP NOT NULL,
	user_ip VARCHAR(30),
	
	user_device VARCHAR(10),
	user_platform VARCHAR(50),
	
	latitude DECIMAL,
	longitude DECIMAL,
	city VARCHAR(50), 
	region VARCHAR(250),
	country VARCHAR(50)
);