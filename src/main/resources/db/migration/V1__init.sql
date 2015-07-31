CREATE TABLE USER (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  first_name VARCHAR(30),
  last_name  VARCHAR(50),
  login      VARCHAR(20),
  password   VARCHAR(60)
);

CREATE TABLE TASK (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY,
  task_name VARCHAR(30),
  done BOOLEAN
);


--INSERT INTO USER (first_name,last_name,login,password) VALUES ('John', 'Rambo','JohnDeath','t454@$#$!@RCF@!E@EX');
--INSERT INTO USER (first_name,last_name,login,password) VALUES ('Johny','Bravo','Bravo007','f4@%@$QTQC42t34tfaadx');