CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  fname text
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id) CONSTRAINT session_id_unique unique,
    pos_row INT NOT NULL CONSTRAINT pos_row_unique unique,
    cell INT NOT NULL CONSTRAINT cell_unique unique,
    user_id INT NOT NULL REFERENCES users(id)
);
