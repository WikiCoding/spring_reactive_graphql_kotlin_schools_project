CREATE TABLE schools(
    id SERIAL NOT NULL PRIMARY KEY,
    school_name VARCHAR(255) NOT NULL,
    UNIQUE(school_name)
);

CREATE TABLE departments(
    dept_id SERIAL NOT NULL PRIMARY KEY,
    dept_name VARCHAR(255) NOT NULL
);