CREATE TABLE TASK (
  id SERIAL PRIMARY KEY,
  task_name VARCHAR(30),
  done      BOOLEAN,
  user_id   INTEGER,

  FOREIGN KEY(user_id) REFERENCES _USER(id)
);