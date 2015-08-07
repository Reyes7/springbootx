CREATE TABLE _USER(
  id SERIAL PRIMARY KEY ,
  first_name VARCHAR(30),
  last_name  VARCHAR(50),
  login      VARCHAR(20),
  password   VARCHAR(60)
);