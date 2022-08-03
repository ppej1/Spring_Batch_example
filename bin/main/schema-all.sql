DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);
INSERT INTO people (first_name,last_name) VALUES	('Kim', 'min');
INSERT INTO people (first_name,last_name) VALUES	('Janny', 'fadev');
INSERT INTO people (first_name,last_name) VALUES	('Billie Eilish', 'fadev');
INSERT INTO people (first_name,last_name) VALUES	('Taylor', 'Swift');
INSERT INTO people (first_name,last_name) VALUES	('Jack', 'masales');

   					
