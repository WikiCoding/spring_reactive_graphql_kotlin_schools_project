CREATE TABLE schools(
    id SERIAL NOT NULL PRIMARY KEY,
    school_name VARCHAR(255) NOT NULL,
    UNIQUE(school_name)
);