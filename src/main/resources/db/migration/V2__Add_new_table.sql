CREATE TABLE TASK (
  id INTEGER PRIMARY KEY,
  task_name VARCHAR(30),
  done      BOOLEAN,
  user_id   INTEGER,

  FOREIGN KEY(user_id) REFERENCES "USER"(id)
);