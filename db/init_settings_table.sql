CREATE TABLE settings (
	id SERIAL PRIMARY KEY,
	name TEXT NOT NULL UNIQUE,
	s_type VARCHAR(20) NOT NULL,
	s_value TEXT
);