CREATE TABLE USER (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  first_name VARCHAR(30),
  last_name  VARCHAR(50)
);

INSERT INTO USER (first_name,last_name) VALUES ('John', 'Rambo');
INSERT INTO USER (first_name,last_name) VALUES ('Johny','Bravo');