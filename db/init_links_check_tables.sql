CREATE TABLE links_check (
	id SERIAL PRIMARY KEY,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP
);

CREATE TABLE checked_links (
	id SERIAL PRIMARY KEY, -- To support Entity mapping (JPA)
	check_id INTEGER REFERENCES links_check(id) ON DELETE RESTRICT,
	link_status VARCHAR(15) NOT NULL,
	link_type VARCHAR(15) NOT NULL,
	target TEXT,
	location TEXT,
	response_code INTEGER,
	redirect_url TEXT
);