-- drop table tickets;
-- drop table users;
-- drop table sessions;
-- drop table halls;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

COMMENT ON table users IS 'Таблица пользователей';
COMMENT ON column users.id IS 'Идентификатор пользователя';
COMMENT ON column users.username IS 'Имя пользователя';
COMMENT ON column users.password IS 'Пароль пользователя';
COMMENT ON column users.email IS 'Email пользователя (для входа)';
COMMENT ON column users.phone IS 'Телефон пользователя (для входа)';



CREATE TABLE halls (
  id SERIAL PRIMARY KEY,
  name text NOT NULL UNIQUE ,
  rows int NOT NULL,
  cells int NOT NULL
);

COMMENT ON table halls IS 'Таблица залов';
COMMENT ON column halls.name IS 'Название зала';
COMMENT ON column halls.rows IS 'Количество рядов в зале';
COMMENT ON column halls.cells IS 'Количество мест в зале';



CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  name text,
  foto BYTEA,
  halls_id int NOT NULL REFERENCES halls(id)
);

comment on table sessions is 'Таблица сеансов';
comment on column sessions.id is 'Идентификатор сеанса';
comment on column sessions.name is 'Название фильма';
comment on column sessions.foto is 'Постер фильма';
comment on column sessions.halls is 'Идентификатор зала';



CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions(id),
    pos_row INT NOT NULL,
    cell INT NOT NULL,
    user_id INT NOT NULL REFERENCES users(id)
);

alter table tickets
add constraint unique_ticket_constrain
unique (session_id, cell, pos_row);

COMMENT ON table tickets IS 'Таблица билетов';
COMMENT ON column tickets.id IS 'Идентификатор билета';
COMMENT ON column tickets.session_id IS 'Идентификатор сессии, внешний ключ';
COMMENT ON column tickets.pos_row IS 'Номер ряда';
COMMENT ON column tickets.cell IS 'Номер места';
COMMENT ON column tickets.user_id IS 'Идентификатор пользователя, внешний ключ';
